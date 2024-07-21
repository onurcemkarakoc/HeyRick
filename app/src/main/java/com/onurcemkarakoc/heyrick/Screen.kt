package com.onurcemkarakoc.heyrick

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Settings : Screen("settings", R.string.settings, Icons.Filled.Settings)
    object CharacterList : Screen("characterList", R.string.character_list, Icons.Filled.Menu)
    object CharacterDetails :
        Screen("characterDetails", R.string.character_details, Icons.Filled.Info)

    object CharacterEpisode :
        Screen("characterEpisode", R.string.character_episode, Icons.Filled.Star)
}