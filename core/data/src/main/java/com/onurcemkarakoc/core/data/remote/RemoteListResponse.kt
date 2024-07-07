package com.onurcemkarakoc.core.data.remote

import com.onurcemkarakoc.core.data.domain.Character
import kotlinx.serialization.Serializable

@Serializable
data class RemoteListResponse(val info: RemoteInfo, val results: List<RemoteCharacter>)

// mapper

fun RemoteListResponse.toDomainCharacterListPair(): Pair<Boolean, List<Character>> {
    return Pair(info.next?.isBlank() == false, results.map { it.toDomainCharacter() })
}
