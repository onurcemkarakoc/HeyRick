package com.onurcemkarakoc.feature.details

import com.onurcemkarakoc.core.common.components.CharacterDetailsDataPoint
import com.onurcemkarakoc.core.data.domain.Character

sealed interface CharacterDetailsViewState {
    data object Loading : CharacterDetailsViewState
    data class Success(
        val character: Character,
        val characterDataPoints: List<CharacterDetailsDataPoint>
    ) : CharacterDetailsViewState

    data class Error(val message: String) : CharacterDetailsViewState
}