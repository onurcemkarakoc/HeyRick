package com.onurcemkarakoc.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.onurcemkarakoc.core.common.R
import com.onurcemkarakoc.core.common.components.SimpleToolbar
import com.onurcemkarakoc.core.data.Theme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getTheme()
        viewModel.getLanguage()
    }

    val state = viewModel.state.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {

        val viewState = state.value

        SimpleToolbar(title = stringResource(id = R.string.settings))
        OutlinedButton(onClick = {
            viewModel.setTheme(Theme.DARK)
        }) {
            Text(text = "Update", color = MaterialTheme.colorScheme.secondary)
        }
        Text(text = viewState.theme.name, color = MaterialTheme.colorScheme.secondary)
    }
}