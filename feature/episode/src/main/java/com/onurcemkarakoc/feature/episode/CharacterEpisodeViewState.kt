package com.onurcemkarakoc.feature.episode

import com.onurcemkarakoc.core.data.domain.Character
import com.onurcemkarakoc.core.data.domain.Episode

sealed interface CharacterEpisodeViewState {

    data object Loading : CharacterEpisodeViewState
    data class Success(
        val character: Character,
        val episodeBySeasonMap: Map<Int, List<Episode>>
    ) : CharacterEpisodeViewState

    data class Error(val message: String) : CharacterEpisodeViewState
}