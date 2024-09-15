package com.onurcemkarakoc.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.onurcemkarakoc.core.common.R
import com.onurcemkarakoc.core.common.components.RadioGroupCompose
import com.onurcemkarakoc.core.common.components.SimpleToolbar
import com.onurcemkarakoc.core.data.Language
import com.onurcemkarakoc.core.data.Theme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllSettings()
    }

    val state = viewModel.state.collectAsState()
    var languageSelection by remember { mutableIntStateOf(0) }
    var themeSelection by remember { mutableIntStateOf(0) }


    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        val viewState = state.value

        languageSelection = Language.entries.indexOfFirst { it == viewState.language }
        themeSelection = Theme.entries.indexOfFirst { it == viewState.theme }

        SimpleToolbar(title = stringResource(id = R.string.settings))
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(id = R.string.language),
            fontSize = 24.sp,
            lineHeight = 24.sp,
            color = MaterialTheme.colorScheme.secondary,
            style = TextStyle(fontStyle = FontStyle.Italic),
            modifier = Modifier.padding(8.dp)
        )

        RadioGroupCompose(
            items = Language.entries.map { it.name },
            selected = languageSelection,
            onSelectionChange = { index ->
                Language.entries[index].let {
                    viewModel.setLanguage(it)
                }
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(id = R.string.theme),
            fontSize = 24.sp,
            lineHeight = 24.sp,
            color = MaterialTheme.colorScheme.secondary,
            style = TextStyle(fontStyle = FontStyle.Italic),
            modifier = Modifier.padding(8.dp)
        )
        RadioGroupCompose(
            items = Theme.entries.map { it.name },
            selected = themeSelection,
            onSelectionChange = { index ->
                Theme.entries[index].let {
                    viewModel.setTheme(it)
                }
            }
        )
    }
}