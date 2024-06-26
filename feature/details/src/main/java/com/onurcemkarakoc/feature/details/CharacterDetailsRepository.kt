package com.onurcemkarakoc.feature.details

import com.onurcemkarakoc.network.ApiOperation
import com.onurcemkarakoc.network.KtorClient
import com.onurcemkarakoc.network.models.domain.Character
import javax.inject.Inject

class CharacterDetailsRepository @Inject constructor(private val ktorClient: KtorClient) {
    suspend fun fetchCharacter(characterId: Int): ApiOperation<Character> {
        return ktorClient.getCharacter(characterId)
    }
}