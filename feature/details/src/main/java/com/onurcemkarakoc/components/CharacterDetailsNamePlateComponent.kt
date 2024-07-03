package com.onurcemkarakoc.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.onurcemkarakoc.core.common.ui.theme.RickPrimary
import com.onurcemkarakoc.core.data.domain.CharacterStatus

@Composable
fun CharacterDetailsNamePlateComponent(
    name: String,
    status: CharacterStatus
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CharacterStatusComponent(characterStatus = status)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontSize = 40.sp,
            color = RickPrimary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 40.sp
        )
    }
}

@Preview
@Composable
fun CharacterDetailsNamePlateComponentPreview() {
    CharacterDetailsNamePlateComponent(
        "Abadango Cluster Princess",
        CharacterStatus.Alive
    )
}