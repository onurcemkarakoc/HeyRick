package com.onurcemkarakoc.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onurcemkarakoc.core.common.components.CharacterDetailsDataPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(private val characterDetailsRepository: CharacterDetailsRepository) :
    ViewModel() {
    private val _state =
        MutableStateFlow<CharacterDetailsViewState>(CharacterDetailsViewState.Loading)

    val state = _state.asStateFlow()

    fun fetchCharacter(characterId: Int) = viewModelScope.launch {
        _state.update { CharacterDetailsViewState.Loading }
        characterDetailsRepository.fetchCharacter(characterId)
            .onSuccess { character ->
                val list = buildList {
                    add(
                        CharacterDetailsDataPoint(
                            title = "Last known location",
                            description = character.location.name
                        )
                    )
                    add(
                        CharacterDetailsDataPoint(
                            title = "Species",
                            description = character.species
                        )
                    )
                    add(
                        CharacterDetailsDataPoint(
                            title = "Gender",
                            description = character.gender.displayName
                        )
                    )
                    character.type.takeIf { it.isNotBlank() }?.let { type ->
                        add(CharacterDetailsDataPoint(title = "Type", description = type))
                    }
                    add(
                        CharacterDetailsDataPoint(
                            title = "Origin",
                            description = character.origin.name
                        )
                    )
                    add(
                        CharacterDetailsDataPoint(
                            title = "Episode count",
                            description = character.episodeUrls.size.toString()
                        )
                    )
                }
                _state.update {
                    CharacterDetailsViewState.Success(
                        character = character,
                        characterDataPoints = list
                    )
                }
            }.onError { exception ->
                _state.update {
                    CharacterDetailsViewState.Error(exception.message.orEmpty())
                }
            }
    }

}