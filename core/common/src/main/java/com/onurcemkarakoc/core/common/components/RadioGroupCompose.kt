package com.onurcemkarakoc.core.common.components

import androidx.compose.runtime.Composable

@Composable
fun RadioGroupCompose(
    items: List<String>,
    selected: Int,
    onSelectionChange: (Int) -> Unit
) {
    items.forEachIndexed { index, item ->
        RadioElement(
            label = item,
            selected = index == selected,
            onClick = { onSelectionChange(index) }
        )
    }
}