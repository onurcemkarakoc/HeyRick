package com.onurcemkarakoc.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.onurcemkarakoc.core.common.R
import com.onurcemkarakoc.core.common.components.SimpleToolbar

@Composable
fun SettingsScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        SimpleToolbar(title = stringResource(id = R.string.settings))
    }
}