package com.onurcemkarakoc.heyrick.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.onurcemkarakoc.heyrick.ui.theme.RickPrimary
import com.onurcemkarakoc.network.models.domain.CharacterStatus

@Composable
fun CharacterDetailsNamePlateComponent(name: String, status: CharacterStatus) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CharacterStatusComponent(status)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name, fontSize = 40.sp, color = RickPrimary)
    }
}

@Preview
@Composable
fun CharacterDetailsNamePlateComponentPreview() {
    CharacterDetailsNamePlateComponent("Rick", CharacterStatus.Alive)
}