package com.onurcemkarakoc.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val characterListRepository: CharacterListRepository) :
    ViewModel() {

    private val _state =
        MutableStateFlow<CharacterListViewState>(CharacterListViewState.Loading)

    val state = _state.asStateFlow()

    var currentPage = 1

    fun fetchCharacterList() = viewModelScope.launch {
        characterListRepository.fetchCharacterList(currentPage)
            .onSuccess { characterList ->
                _state.value = CharacterListViewState.Success(characterList)
            }
            .onError { exception ->
                _state.value = CharacterListViewState.Error(exception.message.orEmpty())
            }
    }
}
