package com.onurcemkarakoc.core.data.domain

import android.graphics.Color
import kotlinx.serialization.Serializable

@Serializable
sealed class CharacterStatus(val displayName:String, val color: Int) {
    data object Alive : CharacterStatus("Alive", Color.GREEN)
    data object Dead : CharacterStatus("Dead", Color.RED)
    data object Unknown : CharacterStatus("Unknown", Color.YELLOW)
}