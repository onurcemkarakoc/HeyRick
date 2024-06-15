package com.onurcemkarakoc.heyrick.screens

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.onurcemkarakoc.heyrick.components.CharacterDetailsDataPoint
import com.onurcemkarakoc.heyrick.components.CharacterDetailsDataPointComponent
import com.onurcemkarakoc.heyrick.components.CharacterDetailsNamePlateComponent
import com.onurcemkarakoc.heyrick.ui.theme.MainBackground
import com.onurcemkarakoc.heyrick.ui.theme.RickPrimary
import com.onurcemkarakoc.network.KtorClient
import com.onurcemkarakoc.network.models.domain.Character
import com.onurcemkarakoc.network.models.domain.CharacterStatus

@Composable
fun CharacterDetailsScreen(
    ktorClient: KtorClient,
    characterId: Int,
    onEpisodeClick: (Int) -> Unit
) {

    var character by remember { mutableStateOf<Character?>(null) }

    var error by remember { mutableStateOf<String?>(null) }

    val characterDataPoints by remember {
        derivedStateOf {
            buildList {
                character?.let {character ->
                    add(CharacterDetailsDataPoint(title = "Last known location", description = character.location.name))
                    add(CharacterDetailsDataPoint(title = "Species", description = character.species))
                    add(CharacterDetailsDataPoint(title = "Gender", description = character.gender.displayName))
                    character.type.takeIf { it.isNotBlank() }?.let { type ->
                        add(CharacterDetailsDataPoint(title = "Type", description = type)) }
                    add(CharacterDetailsDataPoint(title = "Origin", description = character.origin.name))
                    add(CharacterDetailsDataPoint(title = "Episode count", description = character.episodeUrls.size.toString()))
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        ktorClient.getCharacter(characterId)
            .onSuccess { character = it }
            .onError { error = it.message }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackground),
        contentPadding = PaddingValues(16.dp)
    ) {
        if (character == null) {
            item { LoadingState() }
            return@LazyColumn
        }
        item {
            CharacterDetailsNamePlateComponent(
                name = character?.name.orEmpty(),
                status = character?.status ?: CharacterStatus.Unknown
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(shape = RoundedCornerShape(12.dp)),
                model = character?.imageUrl,
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

@Composable
fun LoadingState() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 128.dp),
        color = RickPrimary
    )
}
