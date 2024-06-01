package com.onurcemkarakoc.network

import com.onurcemkarakoc.network.models.domain.Character
import com.onurcemkarakoc.network.models.remote.RemoteCharacter
import com.onurcemkarakoc.network.models.remote.toDomainCharacter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient {
    private val client = HttpClient(OkHttp) {
        defaultRequest {
            url("https://rickandmortyapi.com/api/")
        }
        install(Logging) {
            logger = Logger.SIMPLE
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    suspend fun getCharacter(id: Int): ApiOperation<Character> {
        return safeApiCall {
            client.get("character/$id").body<RemoteCharacter>().toDomainCharacter()
        }
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): ApiOperation<T> {
        return try {
            ApiOperation.Success(apiCall())
        } catch (e: Exception) {
            ApiOperation.Error(e)
        }
    }
}

sealed interface ApiOperation<out T> {
    data class Success<T>(val data: T) : ApiOperation<T>
    data class Error<T>(val exception: Exception) : ApiOperation<T>

    fun onSuccess(block: (T) -> Unit): ApiOperation<T> {
        if (this is Success) {
            block(data)
        }
        return this
    }

    fun onError(block: (Exception) -> Unit): ApiOperation<T> {
        if (this is Error) {
            block(exception)
        }
        return this
    }
}