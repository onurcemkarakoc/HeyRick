package com.onurcemkarakoc.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.onurcemkarakoc.core.data.domain.CharacterStatus

@Composable
fun CharacterStatusComponent(characterStatus: CharacterStatus) {
    Row(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = Color(characterStatus.color),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "Status: ",
            fontSize = 16.sp,
            color = com.onurcemkarakoc.core.common.ui.theme.RickPrimary
        )
        Text(
            text = characterStatus.displayName,
            color = com.onurcemkarakoc.core.common.ui.theme.RickPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun CharacterStatusComponentPreviewAlive() {
    CharacterStatusComponent(CharacterStatus.Alive)
}

@Preview
@Composable
fun CharacterStatusComponentPreviewDead() {
    CharacterStatusComponent(CharacterStatus.Dead)
}

@Preview
@Composable
fun CharacterStatusComponentPreviewUnknown() {
    CharacterStatusComponent(CharacterStatus.Unknown)
}