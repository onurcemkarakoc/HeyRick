package com.onurcemkarakoc.heyrick.screens.episode

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.onurcemkarakoc.heyrick.components.CharacterDetailsDataPoint
import com.onurcemkarakoc.heyrick.components.CharacterDetailsDataPointComponent
import com.onurcemkarakoc.heyrick.components.EpisodeRowComponent
import com.onurcemkarakoc.heyrick.screens.details.LoadingState
import com.onurcemkarakoc.heyrick.ui.theme.MainBackground
import com.onurcemkarakoc.heyrick.ui.theme.RickPrimary

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterEpisodeScreen(
    viewModel: CharacterEpisodeViewModel = hiltViewModel(),
    characterId: Int
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchCharacter(characterId)
    }

    val state = viewModel.state.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackground),
        contentPadding = PaddingValues(16.dp)
    ) {

        when (val viewState = state.value) {
            is CharacterEpisodeViewState.Error -> item {
                Text(
                    text = viewState.message,
                    color = RickPrimary
                )
            }

            is CharacterEpisodeViewState.Loading -> item {
                LoadingState()
            }

            is CharacterEpisodeViewState.Success -> {
                val hasEpisode = viewState.episodeBySeasonMap.any { it.value.isNotEmpty() }
                item {
                    Text(
                        text = viewState.character.name, fontSize = 36.sp, color = RickPrimary
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }

                if (hasEpisode) {
                    item {
                        LazyRow {
                            viewState.episodeBySeasonMap.forEach { mapEntry ->
                                item {
                                    CharacterDetailsDataPointComponent(
                                        dataPoint = CharacterDetailsDataPoint(
                                            "Season ${mapEntry.key}",
                                            mapEntry.value.size.toString().plus(" ep")
                                        )
                                    )
                                }
                                item { Spacer(modifier = Modifier.width(8.dp)) }
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                }
                item {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(shape = RoundedCornerShape(12.dp)),
                        model = viewState.character.imageUrl,
                        contentDescription = "Character image",
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

@Composable
fun SeasonHeader(seasonNumber: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MainBackground)
            .padding(top = 8.dp, bottom = 16.dp)
    ) {
        Text(
            text = "Season $seasonNumber",
            fontSize = 32.sp,
            lineHeight = 32.sp,
            color = RickPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, RickPrimary, RoundedCornerShape(12.dp))
                .padding(8.dp)
        )
    }

}