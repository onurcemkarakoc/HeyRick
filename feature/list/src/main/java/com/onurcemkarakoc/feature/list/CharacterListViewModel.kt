package com.onurcemkarakoc.feature.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onurcemkarakoc.core.data.domain.Character
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

    private var page by mutableIntStateOf(1)
    var canPaginate by mutableStateOf(false)

    val arrayList = mutableListOf<Character>()

    init {
        fetchCharacterList()
    }

    fun fetchCharacterList() = viewModelScope.launch {
        characterListRepository.fetchCharacterList(page)
            .onSuccess { characterListPair ->
                if (page == 1) {
                    arrayList.clear()
                }
                arrayList.addAll(characterListPair.second)
                if (characterListPair.first) {
                    page++
                    canPaginate = true
                } else {
                    canPaginate = false
                }

                _state.value = CharacterListViewState.Success(
                    characterListPair.first,
                    characterListPair.second
                )
            }
            .onError { exception ->
                _state.value = CharacterListViewState.Error(exception.message.orEmpty())
            }
    }
}
