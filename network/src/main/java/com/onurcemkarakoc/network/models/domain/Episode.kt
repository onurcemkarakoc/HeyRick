package com.onurcemkarakoc.network.models.domain

data class Episode(
    val id: Int,
    val name: String,
    val episodeNumber: Int,
    val seasonNumber: Int,
    val airDate: String,
    val characterIdInEpisode: List<Int>
)
