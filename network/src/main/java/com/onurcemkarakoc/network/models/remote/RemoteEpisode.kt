package com.onurcemkarakoc.network.models.remote

import com.onurcemkarakoc.network.models.domain.Episode
import kotlinx.serialization.Serializable

@Serializable
data class RemoteEpisode(
    val id: Int,
    val name: String,
    val air_date: String,
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
        airDate = air_date,
        characterIdInEpisode = characters.map { it.substring(startIndex = it.lastIndexOf("/") + 1).toInt() }
    )
}