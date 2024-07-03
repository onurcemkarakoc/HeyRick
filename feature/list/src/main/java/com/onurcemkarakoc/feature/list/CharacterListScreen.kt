package com.onurcemkarakoc.feature.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.onurcemkarakoc.core.common.components.LoadingState
import com.onurcemkarakoc.core.common.ui.theme.MainBackground
import com.onurcemkarakoc.core.common.ui.theme.RickPrimary

@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel = hiltViewModel(),
    onDetailClick: (characterId: Int) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchCharacterList()
    }

    val state = viewModel.state.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackground),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        when (val viewState = state.value) {
            is CharacterListViewState.Error -> item {
                Text(
                    text = viewState.message,
                    color = RickPrimary
                )
            }

            is CharacterListViewState.Loading -> item { LoadingState() }

            is CharacterListViewState.Success -> {
                val characterList = viewState.characterList
                items(characterList.size) { index ->
                    CharacterListItemComponent(
                        character = characterList.get(index),
                        onDetailClick = onDetailClick
                    )

                }
            }
        }
    }
}