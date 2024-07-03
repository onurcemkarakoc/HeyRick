package com.onurcemkarakoc.feature.list

import com.onurcemkarakoc.core.data.ApiOperation
import com.onurcemkarakoc.core.data.KtorClient
import com.onurcemkarakoc.core.data.domain.Character
import javax.inject.Inject

class CharacterListRepository @Inject constructor(private val ktorClient: KtorClient) {
    suspend fun fetchCharacterList(page: Int): ApiOperation<List<Character>> {
        return ktorClient.getCharacterList(page)
    }
}