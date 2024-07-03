package com.onurcemkarakoc.core.data.domain


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
) {
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

    companion object {
        fun mock() = Character(
            created = "corrumpit",
            episodeUrls = listOf(),
            gender = CharacterGender.Male,
            id = 8282,
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/821.jpeg",
            location = Location(
                name = "Shannon Medina",
                url = "https://search.yahoo.com/search?p=id"
            ),
            name = "Noelle McConnell",
            origin = Origin(
                name = "Dean Burt",
                url = "http://www.bing.com/search?q=noluisse"
            ),
            species = "tortor",
            status = CharacterStatus.Alive,
            type = "aliquet",
            url = "https://duckduckgo.com/?q=tincidunt"
        )
    }
}
