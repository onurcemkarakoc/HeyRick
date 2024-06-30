package com.onurcemkarakoc.feature.episode

import com.onurcemkarakoc.core.data.ApiOperation
import com.onurcemkarakoc.core.data.KtorClient
import com.onurcemkarakoc.core.data.domain.Character
import com.onurcemkarakoc.core.data.domain.Episode
import javax.inject.Inject

class CharacterEpisodeRepository @Inject constructor(private val ktorClient: KtorClient) {
    suspend fun fetchCharacter(characterId: Int): ApiOperation<Character> {
        return ktorClient.getCharacter(characterId)
    }

    suspend fun fetchEpisodes(episodeIds: List<Int>): ApiOperation<List<Episode>> {
        return ktorClient.getEpisodes(episodeIds)
    }
}