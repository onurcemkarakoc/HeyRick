package com.onurcemkarakoc.heyrick.screens.details

import com.onurcemkarakoc.heyrick.components.CharacterDetailsDataPoint
import com.onurcemkarakoc.network.models.domain.Character

sealed interface CharacterDetailsViewState {
    data object Loading : CharacterDetailsViewState
    data class Success(
        val character: Character,
        val characterDataPoints: List<CharacterDetailsDataPoint>
    ) : CharacterDetailsViewState

    data class Error(val message: String) : CharacterDetailsViewState
}