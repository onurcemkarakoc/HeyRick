package com.onurcemkarakoc.heyrick.screens.episode

import com.onurcemkarakoc.network.ApiOperation
import com.onurcemkarakoc.network.KtorClient
import com.onurcemkarakoc.network.models.domain.Character
import com.onurcemkarakoc.network.models.domain.Episode
import javax.inject.Inject

class CharacterEpisodeRepository @Inject constructor(private val ktorClient: KtorClient) {
    suspend fun fetchCharacter(characterId: Int): ApiOperation<Character> {
        return ktorClient.getCharacter(characterId)
    }

    suspend fun fetchEpisodes(episodeIds: List<Int>): ApiOperation<List<Episode>> {
        return ktorClient.getEpisodes(episodeIds)
    }
}