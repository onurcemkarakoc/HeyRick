package com.onurcemkarakoc.feature.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.SubcomposeAsyncImage
import com.onurcemkarakoc.components.CharacterStatusDotComponent
import com.onurcemkarakoc.core.common.components.LoadingState
import com.onurcemkarakoc.core.common.ui.theme.MainBackground
import com.onurcemkarakoc.core.common.ui.theme.RickPrimary
import com.onurcemkarakoc.core.data.domain.Character

@Composable
fun CharacterListItemComponent(character: Character, onDetailClick: (Int) -> Unit) {
    Column(
        Modifier
            .background(MainBackground)
            .clickable {
                onDetailClick(character.id)
            }
    )
    {
        ConstraintLayout {
            val (image, text, status) = createRefs()

            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(text.top)
                    }
                    .aspectRatio(.75f)
                    .clip(shape = RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                model = character.imageUrl,
                contentDescription = "Character image",
                loading = { LoadingState() },
            )

            CharacterStatusDotComponent(
                characterStatus = character.status,
                modifier = Modifier
                    .padding(8.dp)
                    .constrainAs(status) {
                        top.linkTo(image.top)
                        end.linkTo(image.end)
                    }
            )

            Text(
                text = character.name,
                fontSize = 24.sp,
                color = RickPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(text) {
                        top.linkTo(image.bottom)
                        start.linkTo(image.start)
                        end.linkTo(image.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterListItemComponentPreview() {
    CharacterListItemComponent(
        character = Character.mock()
    ) {}
}