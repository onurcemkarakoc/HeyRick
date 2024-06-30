package com.onurcemkarakoc.core.data.domain

import android.graphics.Color
import kotlinx.serialization.Serializable

@Serializable
sealed class CharacterStatus(val displayName:String, val color: Int) {
    object Alive : CharacterStatus("Alive", Color.GREEN)
    object Dead : CharacterStatus("Dead", Color.RED)
    object Unknown : CharacterStatus("Unknown", Color.YELLOW)
}