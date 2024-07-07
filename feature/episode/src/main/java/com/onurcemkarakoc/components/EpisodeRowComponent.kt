package com.onurcemkarakoc.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.onurcemkarakoc.core.common.components.CharacterDetailsDataPoint
import com.onurcemkarakoc.core.common.components.CharacterDetailsDataPointComponent
import com.onurcemkarakoc.core.common.ui.theme.RickPrimary
import com.onurcemkarakoc.feature.episode.R

@Composable
fun EpisodeRowComponent(episode: com.onurcemkarakoc.core.data.domain.Episode) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CharacterDetailsDataPointComponent(
            dataPoint = CharacterDetailsDataPoint(
                stringResource(R.string.episode),
                episode.episodeNumber.toString()
            )
        )
        Spacer(modifier = Modifier.width(64.dp))
        Column {
            Text(
                text = episode.name,
                fontSize = 24.sp,
                color = RickPrimary,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = episode.airDate,
                fontSize = 16.sp,
                color = RickPrimary,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun EpisodeRowComponentPreview() {
    EpisodeRowComponent(
        episode = com.onurcemkarakoc.core.data.domain.Episode(
            id = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            episodeNumber = 1,
            seasonNumber = 1,
            characterIdInEpisode = emptyList()
        )
    )
}