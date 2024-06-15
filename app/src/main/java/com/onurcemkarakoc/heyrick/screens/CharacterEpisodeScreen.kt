package com.onurcemkarakoc.heyrick.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.onurcemkarakoc.heyrick.components.CharacterDetailsDataPoint
import com.onurcemkarakoc.heyrick.components.CharacterDetailsDataPointComponent
import com.onurcemkarakoc.heyrick.components.EpisodeRowComponent
import com.onurcemkarakoc.heyrick.ui.theme.MainBackground
import com.onurcemkarakoc.heyrick.ui.theme.RickPrimary
import com.onurcemkarakoc.network.KtorClient
import com.onurcemkarakoc.network.models.domain.Character
import com.onurcemkarakoc.network.models.domain.Episode
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterEpisodeScreen(
    ktorClient: KtorClient, characterId: Int
) {

    var episodeListState by remember { mutableStateOf<List<Episode>>(emptyList()) }
    var characterState by remember { mutableStateOf<Character?>(null) }

    LaunchedEffect(key1 = Unit) {

        ktorClient.getCharacter(characterId).onSuccess { character ->
                characterState = character
                launch {
                    ktorClient.getEpisodes(character.episodeUrls.map {
                        it.substringAfterLast("/").toInt()
                    }).onSuccess { episodeListState = it }.onError {}
                }
            }.onError {}

    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackground),
        contentPadding = PaddingValues(16.dp)
    ) {
        val episodeBySeasonMap = episodeListState.groupBy { it.seasonNumber }

        if (episodeListState.isEmpty()) {
            item { LoadingState() }
            return@LazyColumn
        }

        item {
            Text(
                text = characterState?.name.orEmpty(), fontSize = 36.sp, color = RickPrimary
            )
        }
        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            LazyRow {
                episodeBySeasonMap.forEach { mapEntry ->
                    item {
                        CharacterDetailsDataPointComponent(
                            dataPoint = CharacterDetailsDataPoint(
                                "Season ${mapEntry.key}", mapEntry.value.size.toString().plus(" ep")
                            )
                        )
                    }
                    item { Spacer(modifier = Modifier.width(8.dp)) }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(shape = RoundedCornerShape(12.dp)),
                model = characterState?.imageUrl,
                contentDescription = "Character image",
                loading = { LoadingState() },
            )
        }
        println("Episode by season map: $episodeBySeasonMap")

        episodeBySeasonMap.forEach { mapEntry ->
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