package com.onurcemkarakoc.feature.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterEpisodeViewModel @Inject constructor(private val characterEpisodeRepository: CharacterEpisodeRepository) :
    ViewModel() {
    private val _state =
        MutableStateFlow<CharacterEpisodeViewState>(CharacterEpisodeViewState.Loading)

    val state = _state.asStateFlow()

    fun fetchCharacter(characterId: Int) = viewModelScope.launch {
        _state.update { CharacterEpisodeViewState.Loading }
        characterEpisodeRepository.fetchCharacter(characterId).onSuccess { character ->
            launch {
                characterEpisodeRepository.fetchEpisodes(character.episodeUrls.map { episodeUrl ->
                    episodeUrl.substringAfterLast("/").toInt()
                }).onSuccess { episodeList ->
                    _state.update { _ ->
                        CharacterEpisodeViewState.Success(
                            character = character,
                            episodeBySeasonMap = episodeList.groupBy { it.seasonNumber }
                        )
                    }
                }.onError { onError(it) }
            }
        }.onError { onError(it) }
    }

    private fun onError(exception: Exception) {
        _state.update {
            CharacterEpisodeViewState.Error(exception.message.orEmpty())
        }
    }
}