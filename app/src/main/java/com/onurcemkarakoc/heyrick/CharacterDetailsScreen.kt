package com.onurcemkarakoc.heyrick

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.onurcemkarakoc.heyrick.ui.CharacterDetailsDataPoint
import com.onurcemkarakoc.heyrick.ui.CharacterDetailsDataPointComponent
import com.onurcemkarakoc.heyrick.ui.theme.MainBackground
import com.onurcemkarakoc.network.KtorClient
import com.onurcemkarakoc.network.models.domain.Character
import com.onurcemkarakoc.network.models.domain.CharacterStatus
import kotlinx.coroutines.delay

@Composable
fun CharacterDetailsScreen(
    ktorClient: KtorClient,
    characterId: Int
) {

    var character by remember { mutableStateOf<Character?>(null) }

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
        delay(2000)
        character = ktorClient.getCharacter(characterId)
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
        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            // image
        }
        items(characterDataPoints) { dataPoint ->
            Spacer(modifier = Modifier.height(8.dp))
            CharacterDetailsDataPointComponent(dataPoint)
        }
    }
}

@Composable
fun LoadingState() {
    CircularProgressIndicator()
}
