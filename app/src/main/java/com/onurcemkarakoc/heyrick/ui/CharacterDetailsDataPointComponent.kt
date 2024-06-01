package com.onurcemkarakoc.heyrick.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Column(Modifier.fillMaxWidth()) {
        Text(text = dataPoint.title, fontSize = 16.sp, color = RickPrimary)
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