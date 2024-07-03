package com.onurcemkarakoc.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onurcemkarakoc.core.data.domain.CharacterStatus


@Composable
fun CharacterStatusDotComponent(characterStatus: CharacterStatus, modifier: Modifier = Modifier) {
    Box(
        modifier
            .shadow(10.dp, RoundedCornerShape(50))
            .width(12.dp)
            .height(12.dp)
            .background(Color(characterStatus.color))
    ) {
    }

}

@Preview
@Composable
fun CharacterStatusDotComponentAlivePreview() {
    CharacterStatusDotComponent(CharacterStatus.Alive)
}

@Preview
@Composable
fun CharacterStatusDotComponentDeadPreview() {
    CharacterStatusDotComponent(CharacterStatus.Dead)
}

@Preview
@Composable
fun CharacterStatusDotComponentUnknownPreview() {
    CharacterStatusDotComponent(CharacterStatus.Unknown)
}