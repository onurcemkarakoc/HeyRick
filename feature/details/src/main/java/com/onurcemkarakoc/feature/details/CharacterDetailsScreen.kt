package com.onurcemkarakoc.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.onurcemkarakoc.components.CharacterStatusComponent
import com.onurcemkarakoc.core.common.components.CharacterDetailsDataPointComponent
import com.onurcemkarakoc.core.common.components.LoadingState
import com.onurcemkarakoc.core.common.components.SimpleToolbar

@Composable
fun CharacterDetailsScreen(
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    characterId: Int,
    onBackPressedClick: () -> Unit,
    onEpisodeClick: (Int) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchCharacter(characterId)
    }

    val state = viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        val viewState = state.value
        val title = when (viewState) {
            is CharacterDetailsViewState.Success -> viewState.character.name
            else -> stringResource(R.string.character_details)
        }
        SimpleToolbar(title = title) {
            onBackPressedClick.invoke()
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {

            when (viewState) {
                is CharacterDetailsViewState.Error -> item {
                    Text(
                        text = viewState.message,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                is CharacterDetailsViewState.Loading -> item { LoadingState() }
                is CharacterDetailsViewState.Success -> {
                    val character = viewState.character
                    val characterDataPoints = viewState.characterDataPoints
                    item {
                        CharacterStatusComponent(character.status)
                    }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                    item {
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(shape = RoundedCornerShape(12.dp)),
                            model = character.imageUrl,
                            contentDescription = stringResource(com.onurcemkarakoc.core.common.R.string.character_image),
                            loading = { LoadingState() },
                        )
                    }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                    items(characterDataPoints) { dataPoint ->
                        Spacer(modifier = Modifier.height(16.dp))
                        CharacterDetailsDataPointComponent(dataPoint)
                    }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.view_all_episodes),
                                modifier = Modifier
                                    .fillMaxWidth(.9f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(
                                        1.dp,
                                        MaterialTheme.colorScheme.secondary,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        onEpisodeClick(characterId)
                                    }
                                    .padding(8.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                textAlign = TextAlign.Center,
                            )
                        }

                    }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            }
        }
    }
}
