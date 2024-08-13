package com.onurcemkarakoc.core.data

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val theme: Theme = Theme.SYSTEM,
    val language: Language = Language.ENGLISH
)

enum class Theme {
    SYSTEM, LIGHT, DARK
}

enum class Language {
    ENGLISH, TURKISH
}
