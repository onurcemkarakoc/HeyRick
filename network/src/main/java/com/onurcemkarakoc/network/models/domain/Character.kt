package com.onurcemkarakoc.network.models.domain


import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val created: String,
    val episodeUrls: List<String>,
    val gender: CharacterGender,
    val id: Int,
    val imageUrl: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: CharacterStatus,
    val type: String,
    val url: String
){
    @Serializable
    data class Origin(
        val name: String,
        val url: String
    )
    @Serializable
    data class Location(
        val name: String,
        val url: String
    )
}