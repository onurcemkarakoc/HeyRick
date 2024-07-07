package com.onurcemkarakoc.core.data

import com.onurcemkarakoc.core.data.domain.Character
import com.onurcemkarakoc.core.data.domain.Episode
import com.onurcemkarakoc.core.data.remote.RemoteCharacter
import com.onurcemkarakoc.core.data.remote.RemoteEpisode
import com.onurcemkarakoc.core.data.remote.RemoteListResponse
import com.onurcemkarakoc.core.data.remote.toDomainCharacter
import com.onurcemkarakoc.core.data.remote.toDomainCharacterListPair
import com.onurcemkarakoc.core.data.remote.toDomainEpisode
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

    private var characterCache: MutableMap<Int, Character> = mutableMapOf()

    private suspend fun getEpisode(episodeId: Int): ApiOperation<Episode> {
        return safeApiCall {
            client.get("episode/$episodeId")
                .body<RemoteEpisode>()
                .toDomainEpisode()
        }
    }

    suspend fun getEpisodes(episodeIds: List<Int>): ApiOperation<List<Episode>> {
        if (episodeIds.size == 1) {
            return getEpisode(episodeIds.first()).onSuccess { listOf(it) }.map {
                listOf(it)
            }
        }
        val ids = episodeIds.joinToString(",")
        return safeApiCall {
            client.get("episode/$ids")
                .body<List<RemoteEpisode>>()
                .map { it.toDomainEpisode() }
        }
    }

    suspend fun getCharacter(id: Int): ApiOperation<Character> {
        characterCache[id]?.let {
            return ApiOperation.Success(it)
        }
        return safeApiCall {
            client.get("character/$id").body<RemoteCharacter>().toDomainCharacter().also {
                characterCache[id] = it
            }
        }
    }

    suspend fun getCharacterList(page: Int = 1): ApiOperation<Pair<Boolean, List<Character>>> {
        return safeApiCall {
            client.get("character/?page=$page").body<RemoteListResponse>()
                .toDomainCharacterListPair()
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

    fun <R> map(block: (T) -> R): ApiOperation<R> {
        return when (this) {
            is Success -> Success(block(data))
            is Error -> Error(exception)
        }
    }

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