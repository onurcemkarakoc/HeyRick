package com.onurcemkarakoc.feature.list

import com.onurcemkarakoc.core.data.domain.Character


sealed interface CharacterListViewState {
    data object Loading : CharacterListViewState
    data class Success(val characterList: List<Character>) :
        CharacterListViewState

    data class Error(val message: String) : CharacterListViewState
}