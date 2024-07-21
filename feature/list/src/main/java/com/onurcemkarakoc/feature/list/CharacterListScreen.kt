package com.onurcemkarakoc.feature.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.onurcemkarakoc.core.common.components.LoadingState
import com.onurcemkarakoc.core.common.components.SimpleToolbar

@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel = hiltViewModel(),
    onDetailClick: (characterId: Int) -> Unit
) {
    val lazyColumnListGridState = rememberLazyGridState()

    val shouldStartPaginate = remember {
        derivedStateOf {
            viewModel.canPaginate
                    && (lazyColumnListGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -9) >= (lazyColumnListGridState.layoutInfo.totalItemsCount - 6)
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value) {
            viewModel.fetchCharacterList()
        }
    }

    val state = viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        SimpleToolbar(title = stringResource(R.string.rick_and_morty_characters))
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            state = lazyColumnListGridState,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            when (val viewState = state.value) {
                is CharacterListViewState.Error -> item {
                    Text(
                        text = viewState.message,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                is CharacterListViewState.Loading -> item { LoadingState() }

                is CharacterListViewState.Success -> {
                    val characterList = viewModel.arrayList
                    items(characterList.size) { index ->
                        CharacterListItemComponent(
                            character = characterList[index],
                            onDetailClick = onDetailClick
                        )
                    }
                }
            }
        }
    }
}