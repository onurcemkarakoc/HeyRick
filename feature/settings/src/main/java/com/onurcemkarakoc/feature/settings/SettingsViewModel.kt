package com.onurcemkarakoc.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onurcemkarakoc.core.data.AppSettings
import com.onurcemkarakoc.core.data.Language
import com.onurcemkarakoc.core.data.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsRepository: SettingsRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(AppSettings())
    val state = _state.asStateFlow()

    fun getTheme() = viewModelScope.launch {
        val theme = settingsRepository.getTheme()
        _state.update {
            it.copy(theme = theme)
        }
    }

    fun setTheme(theme: Theme) = viewModelScope.launch {
        settingsRepository.setTheme(theme)
        getTheme()
    }

    fun getLanguage() = viewModelScope.launch {
        val language = settingsRepository.getLanguage()
        _state.update {
            it.copy(language = language)
        }
    }

    fun setLanguage(language: Language) = viewModelScope.launch {
        settingsRepository.setLanguage(language)
        getLanguage()
    }

}
