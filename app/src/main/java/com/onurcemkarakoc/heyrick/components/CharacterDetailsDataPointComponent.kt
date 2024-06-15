package com.onurcemkarakoc.heyrick.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.onurcemkarakoc.heyrick.ui.theme.RickPrimary
import com.onurcemkarakoc.heyrick.ui.theme.RickSecondary

data class CharacterDetailsDataPoint(
    val title: String,
    val description: String
)
@Composable
fun CharacterDetailsDataPointComponent(dataPoint: CharacterDetailsDataPoint) {
    Column {
        Text(text = dataPoint.title, fontSize = 12.sp, color = RickPrimary)
        Text(text = dataPoint.description, fontSize = 20.sp, color = RickSecondary)
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