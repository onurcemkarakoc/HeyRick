package com.onurcemkarakoc.heyrick

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.onurcemkarakoc.core.common.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    data object Settings : Screen("settings", R.string.settings, Icons.Filled.Settings)
    data object CharacterList : Screen("characterList", R.string.character_list, Icons.Filled.Menu)
    data object CharacterDetails :
        Screen("characterDetails", R.string.character_details, Icons.Filled.Info)

    data object CharacterEpisode :
        Screen("characterEpisode", R.string.character_episode, Icons.Filled.Star)
}