package com.onurcemkarakoc.core.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RadioElement(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick() }, verticalAlignment = Alignment.CenterVertically
    ) {

        RadioButton(
            selected = selected, onClick = null, colors = RadioButtonColors(
                selectedColor = MaterialTheme.colorScheme.secondary,
                unselectedColor = MaterialTheme.colorScheme.secondary,
                disabledSelectedColor = MaterialTheme.colorScheme.primary,
                disabledUnselectedColor = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = label,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}