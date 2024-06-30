package com.onurcemkarakoc.feature.details

import com.onurcemkarakoc.core.data.ApiOperation
import com.onurcemkarakoc.core.data.KtorClient
import com.onurcemkarakoc.core.data.domain.Character
import javax.inject.Inject

class CharacterDetailsRepository @Inject constructor(private val ktorClient: KtorClient) {
    suspend fun fetchCharacter(characterId: Int): ApiOperation<Character> {
        return ktorClient.getCharacter(characterId)
    }
}