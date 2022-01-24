package com.example.templateapp01.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.templateapp01.data.ErrorResult
import com.example.templateapp01.ui.components.*
import com.example.templateapp01.ui.components.FullScreenLoading
import com.example.templateapp01.ui.extentions.mainContentPadding
import com.example.templateapp01.ui.theme.TemplateApp01Theme

// Stateful Composable that depends on ViewModel.
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToNextScreen: (String) -> Unit,
) {
    HomeContent(
        uiState = viewModel.uiState,
        onClickReload = { viewModel.searchPhotos() },
        onClickPhotoItem = navigateToNextScreen,
        modifier = modifier
    )
}

// stateless Composable.
@Composable
internal fun HomeContent(
    uiState: HomeUiState,
    onClickReload: () -> Unit,
    onClickPhotoItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
    ) {
        when (uiState) {
            is HomeUiState.Error -> {
                ErrorMessage(
                    message = "fetch error: ${uiState.error.message}",
                    onClickReload = onClickReload,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
            HomeUiState.Initial, HomeUiState.Loading -> {
                FullScreenLoading()
            }
            HomeUiState.NoPhotos -> {
                ErrorMessage(
                    message = "empty list.",
                    onClickReload = onClickReload,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
            is HomeUiState.Photos -> {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    itemsIndexed(uiState.results) { index, photo ->
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

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4,
    showSystemUi = true
)
@Composable
fun HomeContent_Preview_Loading() {
    TemplateApp01Theme {
        HomeContent(
            uiState = HomeUiState.Loading,
            onClickReload = { },
            onClickPhotoItem = {},
            modifier = Modifier.mainContentPadding(PaddingValues(12.dp, 12.dp, 12.dp, 92.dp))
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4,
    showSystemUi = true
)
@Composable
fun HomeContent_Preview_Error() {
    TemplateApp01Theme {
        HomeContent(
            uiState = HomeUiState.Error(error = ErrorResult.NetworkError("error error...")),
            onClickReload = { },
            onClickPhotoItem = {},
            modifier = Modifier.mainContentPadding(PaddingValues(12.dp, 12.dp, 12.dp, 92.dp))
        )
    }
}