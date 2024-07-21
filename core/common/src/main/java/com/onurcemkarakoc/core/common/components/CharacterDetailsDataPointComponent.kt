package com.onurcemkarakoc.core.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

data class CharacterDetailsDataPoint(
    val title: String,
    val description: String
)
@Composable
fun CharacterDetailsDataPointComponent(dataPoint: CharacterDetailsDataPoint) {
    Column {
        Text(text = dataPoint.title, fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)
        Text(
            text = dataPoint.description,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Preview
@Composable
fun CharacterDetailsDataPointComponentPreview() {
    CharacterDetailsDataPointComponent(
        CharacterDetailsDataPoint(
            title = "Species",
            description = "Human"
        )
    )
}