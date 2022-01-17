package com.example.templateapp01.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.templateapp01.data.ErrorResult
import com.example.templateapp01.ui.components.*
import com.example.templateapp01.ui.components.FullScreenLoading
import com.example.templateapp01.ui.theme.TemplateApp01Theme

// Stateful Composable that depends on ViewModel.
@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToNextScreen: (String) -> Unit
) {
    HomeContent(
        uiState = viewModel.uiState,
        onClickReload = { viewModel.searchPhotos() },
        onClickPhotoItem = navigateToNextScreen
    )
}

// stateless Composable.
@Composable
internal fun HomeContent(
    uiState: UiState,
    onClickReload: () -> Unit,
    onClickPhotoItem: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
    ) {
        when (val ret = uiState) {
            is UiState.Error -> {
                ErrorMessage(
                    message = "fetch error: ${ret.error.message}",
                    onClickReload = onClickReload,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
            UiState.Initial, UiState.Loading -> {
                FullScreenLoading()
            }
            UiState.NoPhotos -> {
                ErrorMessage(
                    message = "empty list.",
                    onClickReload = onClickReload,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
            is UiState.Photos -> {
                LazyRow(
                    // modifier = modifier,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    // contentPadding = PaddingValues(start = 24.dp, end = 24.dp),
                ) {
                    itemsIndexed(ret.results) { index, photo ->
                        PhotoItem(
                            photo = photo,
                            onClick = {
                                onClickPhotoItem(it)
                            },
                            modifier = Modifier
                                .size(120.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreviewLoading() {
    TemplateApp01Theme {
        HomeContent(uiState = UiState.Loading, onClickReload = { }, onClickPhotoItem = {})
        HomeScreen(navigateToNextScreen = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreviewError() {
    TemplateApp01Theme {
        HomeContent(
            uiState = UiState.Error(error = ErrorResult.NetworkError("error error...")),
            onClickReload = { },
            onClickPhotoItem = {})
        HomeScreen(navigateToNextScreen = {})
    }
}