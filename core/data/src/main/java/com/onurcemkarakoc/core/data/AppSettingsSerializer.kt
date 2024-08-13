package com.onurcemkarakoc.core.data

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object AppSettingsSerializer : Serializer<AppSettings> {
    override val defaultValue: AppSettings
        get() = AppSettings()

    override suspend fun readFrom(input: InputStream): AppSettings =
        try {
            Json.decodeFromString(AppSettings.serializer(), input.readBytes().decodeToString())
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }

    override suspend fun writeTo(t: AppSettings, output: OutputStream) =
        withContext(Dispatchers.IO) {
            output.write(Json.encodeToString(AppSettings.serializer(), t).toByteArray())
        }
}