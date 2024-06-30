package com.onurcemkarakoc.core.data.remote

import com.onurcemkarakoc.core.data.domain.Episode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteEpisode(
    val id: Int,
    val name: String,
    @SerialName("air_date")
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)

// mapper

fun RemoteEpisode.toDomainEpisode(): Episode {
    return Episode(
        id = id,
        name = name,
        episodeNumber = episode.filter { it.isDigit() }.take(2).toInt(),
        seasonNumber = episode.filter { it.isDigit() }.takeLast(2).toInt(),
        airDate = airDate,
        characterIdInEpisode = characters.map { it.substring(startIndex = it.lastIndexOf("/") + 1).toInt() }
    )
}