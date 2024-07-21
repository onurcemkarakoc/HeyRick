package com.onurcemkarakoc.core.data.domain

import kotlinx.serialization.Serializable

@Serializable
sealed class CharacterGender(val displayName: String) {
    data object Male : CharacterGender("Male")
    data object Female : CharacterGender("Female")
    data object Genderless : CharacterGender("No gender")
    data object Unknown : CharacterGender("Not specified")
}