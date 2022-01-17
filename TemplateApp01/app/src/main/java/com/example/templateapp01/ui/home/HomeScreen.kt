package com.example.templateapp01.ui.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.templateapp01.model.UnSplashPhoto
import com.example.templateapp01.ui.components.FullScreenLoading
import com.example.templateapp01.ui.components.MyAppSurface
import com.example.templateapp01.ui.components.PhotoImage

@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by remember { viewModel.uiState }
    Log.d(HomeViewModel.LOG_TAG, "uiState: $uiState")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
    ) {
        when (val ret = uiState) {
            is UiState.Error -> {
                Text(text = "fetch error: " + ret.error.message)
            }
            UiState.Initial, UiState.Loading -> {
                FullScreenLoading()
            }
            UiState.NoPhotos -> {
                Text(text = "empty list.")
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
                                Log.d("HomeScreen", "PhotoItem Clicked! id: $it")
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

@Composable
fun PhotoItem(
    photo: UnSplashPhoto,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    MyAppSurface(
        border = BorderStroke(1.dp, Color.Green),
        // modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable(onClick = { onClick(photo.id) })
                .padding(8.dp)
        ) {
            PhotoImage(
                imageUrl = photo.urls.regular,
                elevation = 4.dp,
                contentDescription = photo.user.username,
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = photo.user.username,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}