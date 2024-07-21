package com.onurcemkarakoc.feature.episode

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.onurcemkarakoc.components.EpisodeRowComponent
import com.onurcemkarakoc.core.common.components.CharacterDetailsDataPoint
import com.onurcemkarakoc.core.common.components.CharacterDetailsDataPointComponent
import com.onurcemkarakoc.core.common.components.LoadingState
import com.onurcemkarakoc.core.common.components.SimpleToolbar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterEpisodeScreen(
    viewModel: CharacterEpisodeViewModel = hiltViewModel(),
    characterId: Int,
    onBackPressedClick: () -> Unit
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
            is CharacterEpisodeViewState.Success -> stringResource(
                R.string.s_episodes,
                viewState.character.name
            )

            else -> stringResource(id = R.string.character_episodes)
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
                is CharacterEpisodeViewState.Error -> item {
                    Text(
                        text = viewState.message,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                is CharacterEpisodeViewState.Loading -> item {
                    LoadingState()
                }

                is CharacterEpisodeViewState.Success -> {
                    val hasEpisode = viewState.episodeBySeasonMap.any { it.value.isNotEmpty() }
                    if (hasEpisode) {
                        item {
                            LazyRow {
                                viewState.episodeBySeasonMap.forEach { mapEntry ->
                                    item {
                                        CharacterDetailsDataPointComponent(
                                            dataPoint = CharacterDetailsDataPoint(
                                                stringResource(
                                                    R.string.season,
                                                    mapEntry.key.toString()
                                                ),
                                                stringResource(
                                                    R.string.ep,
                                                    mapEntry.value.size.toString()
                                                )
                                            )
                                        )
                                    }
                                    item { Spacer(modifier = Modifier.width(8.dp)) }
                                }
                            }
                        }
                        item { Spacer(modifier = Modifier.height(16.dp)) }
                    }
                    item {
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(shape = RoundedCornerShape(12.dp)),
                            model = viewState.character.imageUrl,
                            contentDescription = stringResource(com.onurcemkarakoc.core.common.R.string.character_image),
                            loading = { LoadingState() },
                        )
                    }

                    if (hasEpisode) {
                        viewState.episodeBySeasonMap.forEach { mapEntry ->
                            stickyHeader {
                                Surface(Modifier.fillMaxWidth()) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    SeasonHeader(mapEntry.key)
                                }
                            }
                            items(mapEntry.value) { episode ->
                                Spacer(modifier = Modifier.height(16.dp))
                                EpisodeRowComponent(episode = episode)
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun SeasonHeader(seasonNumber: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 8.dp, bottom = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.season, seasonNumber),
            fontSize = 32.sp,
            lineHeight = 32.sp,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp)
                )
                .padding(8.dp)
        )
    }

}