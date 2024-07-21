package com.onurcemkarakoc.core.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.onurcemkarakoc.core.common.R

@Composable
fun SimpleToolbar(
    modifier: Modifier = Modifier,
    title: String,
    isTitleCenter: Boolean = false,
    onBackClick: (() -> Unit)? = null,
) {
    Column(
        modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            if (onBackClick != null)
                Image(
                    modifier = modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp)
                        .clickable {
                            onBackClick.invoke()
                        },
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = stringResource(R.string.back_arrow)
                )
            Text(
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp),
                fontSize = 30.sp,
                lineHeight = 30.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = title, color = MaterialTheme.colorScheme.secondary,
                textAlign = if (isTitleCenter) TextAlign.Center
                else TextAlign.Start
            )
        }
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
                .height(.25.dp)
        )
    }
}

@Preview
@Composable
fun SimpleToolbarPreview() {
    SimpleToolbar(
        title = "Title",
        isTitleCenter = false,
        onBackClick = {}
    )
}