package com.example.templateapp01.ui.search

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.templateapp01.data.ErrorResult
import com.example.templateapp01.model.UnSplashPhoto
import com.example.templateapp01.ui.components.*
import com.example.templateapp01.ui.components.FullScreenLoading
import com.example.templateapp01.ui.theme.TemplateApp01Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResultScreen(
    viewModel: SearchResultViewModel = hiltViewModel(),
    navController: NavHostController,
    query: String
) {
    ResultContent(
        uiState = viewModel.uiState,
        navController = navController,
        onClickReload = { viewModel.searchPhotos(query) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResultContent(
    uiState: SearchResultUiState,
    navController: NavHostController,
    onClickReload: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "ResultScreen")
                },
                navigationIcon = {
                    NavigateBackIconButton(navController = navController)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            when (val ret = uiState) {
                SearchResultUiState.Initial -> {
                    onClickReload()
                    Log.d("ResultScreen", "Initial")
                }
                SearchResultUiState.Loading -> {
                    FullScreenLoading()
                }
                SearchResultUiState.NoPhotos -> {
                    ErrorMessage(
                        message = "empty list.",
                        onClickReload = onClickReload,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                }
                is SearchResultUiState.Photos -> {
                    PhotoItem(
                        photo = ret.results.last(),
                        onClick = {
                            Log.d("ResultScreen", "PhotoItem Clicked! id: $it")
                        },
                        modifier = Modifier
                            .size(120.dp)
                    )
                }
                is SearchResultUiState.Error -> {
                    ErrorMessage(
                        message = "fetch error." + ret.result.message,
                        onClickReload = onClickReload,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            NavigateBackButton(navController = navController, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultContentPreviewInit() {
    TemplateApp01Theme {
        ResultContent(
            uiState = SearchResultUiState.Initial,
            navController = rememberNavController(),
            onClickReload = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ResultContentPreviewEmpty() {
    TemplateApp01Theme {
        ResultContent(
            uiState = SearchResultUiState.NoPhotos,
            navController = rememberNavController(),
            onClickReload = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ResultContentPreviewSuccess() {
    val photos = mutableListOf<UnSplashPhoto>()
    repeat(10) {
        photos.add(
            UnSplashPhoto(
                id = "0",
                urls = UnSplashPhoto.UnSplashPhotoUrls(
                    full = "",
                    regular = "https://images.unsplash.com/photo-1529778873920-4da4926a72c2?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwyMDY4NDh8MHwxfHNlYXJjaHwxMHx8Y2F0fGVufDB8fHx8MTY0MjQyODYxOQ&ixlib=rb-1.2.1&q=80&w=1080",
                ),
                likes = 0,
                user = UnSplashPhoto.UnSplashUser(
                    username = "e_d_g_a_r",
                    profileImage = UnSplashPhoto.UnSplashUser.ProfileImage(null)
                )
            )
        )
    }
    val navController = rememberNavController()
    TemplateApp01Theme {
        ResultContent(
            uiState = SearchResultUiState.Photos(photos),
            navController = navController,
            onClickReload = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ResultContentPreviewLoading() {
    TemplateApp01Theme {
        ResultContent(
            uiState = SearchResultUiState.Loading,
            navController = rememberNavController(),
            onClickReload = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ResultContentPreviewError() {
    TemplateApp01Theme {
        ResultContent(
            uiState = SearchResultUiState.Error(result = ErrorResult.NetworkError("error error error")),
            navController = rememberNavController(),
            onClickReload = {})
    }
}