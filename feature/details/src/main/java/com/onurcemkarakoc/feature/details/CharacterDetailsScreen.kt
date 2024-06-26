package com.onurcemkarakoc.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.onurcemkarakoc.components.CharacterDetailsNamePlateComponent
import com.onurcemkarakoc.core.common.components.CharacterDetailsDataPointComponent
import com.onurcemkarakoc.core.common.components.LoadingState
import com.onurcemkarakoc.core.common.ui.theme.MainBackground
import com.onurcemkarakoc.core.common.ui.theme.RickPrimary


@Composable
fun CharacterDetailsScreen(
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    characterId: Int,
    onEpisodeClick: (Int) -> Unit
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
            is CharacterDetailsViewState.Error -> item {
                Text(
                    text = viewState.message,
                    color = RickPrimary
                )
            }

            is CharacterDetailsViewState.Loading -> item { LoadingState() }
            is CharacterDetailsViewState.Success -> {
                val character = viewState.character
                val characterDataPoints = viewState.characterDataPoints
                item {
                    CharacterDetailsNamePlateComponent(
                        name = character.name,
                        status = character.status
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(shape = RoundedCornerShape(12.dp)),
                        model = character.imageUrl,
                        contentDescription = "Character image",
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
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "View All Episodes",
                            modifier = Modifier
                                .fillMaxWidth(.9f)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, RickPrimary, RoundedCornerShape(12.dp))
                                .clickable {
                                    onEpisodeClick(characterId)
                                }
                                .padding(8.dp),
                            color = RickPrimary,
                            textAlign = TextAlign.Center,
                        )
                    }

                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }

    }
}
