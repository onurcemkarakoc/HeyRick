package com.onurcemkarakoc.feature.settings

import androidx.datastore.core.DataStore
import com.onurcemkarakoc.core.data.AppSettings
import com.onurcemkarakoc.core.data.Language
import com.onurcemkarakoc.core.data.Theme
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class SettingsRepository @Inject constructor(private val dataStoreAppSettings: DataStore<AppSettings>) {

    suspend fun getTheme() = getAppSettings().theme

    suspend fun setTheme(theme: Theme) {
        val appSettings = getAppSettings()
        saveAppSettings(appSettings.copy(theme = theme))
    }

    suspend fun getLanguage() = getAppSettings().language

    suspend fun setLanguage(language: Language) {
        val appSettings = getAppSettings()
        saveAppSettings(appSettings.copy(language = language))
    }

    private suspend fun getAppSettings() = dataStoreAppSettings.data.catch {
        it.printStackTrace()
        emit(AppSettings())
    }.first()

    private suspend fun saveAppSettings(appSettings: AppSettings) {
        dataStoreAppSettings.updateData {
            appSettings
        }
    }
}