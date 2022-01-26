package com.example.templateapp01.ui.search

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.templateapp01.data.ErrorResult
import com.example.templateapp01.model.UnSplashPhoto
import com.example.templateapp01.ui.components.*
import com.example.templateapp01.ui.components.FullScreenLoading
import com.example.templateapp01.ui.extentions.mainContentPadding
import com.example.templateapp01.ui.theme.TemplateApp01Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResultScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchResultViewModel = hiltViewModel(),
    navController: NavHostController,
    query: String,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    // show SnackBar.
    var errorMessage: String? by remember { mutableStateOf(null) }
    LaunchedEffect(key1 = errorMessage, block = {
        Log.d("ResultContent", "LaunchedEffect: $errorMessage")
        errorMessage?.let { scaffoldState.snackbarHostState.showSnackbar(it) }
    })

    // TODO: refactor material3
    androidx.compose.material.Scaffold(
        scaffoldState = scaffoldState
    ) {
        ResultContent(
            uiState = viewModel.uiState,
            navController = navController,
            onClickReload = { viewModel.searchPhotos(query) },
            modifier = modifier,
            onError = { errorMessage = it }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResultContent(
    modifier: Modifier = Modifier,
    uiState: SearchResultUiState,
    navController: NavHostController,
    onClickReload: () -> Unit,
    onError: (String) -> Unit,
) {
    Log.d("ResultContent", "uiState: $uiState")
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
            modifier = modifier,
            verticalArrangement = Arrangement.Center
        ) {
            when (uiState) {
                SearchResultUiState.SearchPhotos -> {
                    onClickReload()
                }
                SearchResultUiState.Initial, SearchResultUiState.Loading -> {
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
                        photo = uiState.results.last(),
                        onClick = {
                            Log.d("ResultScreen", "PhotoItem Clicked! id: $it")
                        },
                        modifier = Modifier
                            .size(120.dp)
                    )
                }
                is SearchResultUiState.Error -> {
                    ErrorMessage(
                        message = "fetch error." + uiState.result.message,
                        onClickReload = onClickReload,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                    onError(uiState.result.message ?: "error.")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            NavigateBackButton(navController = navController, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4,
    showSystemUi = false
)
@Composable
fun ResultContent_Preview_Init() {
    TemplateApp01Theme {
        ResultContent(
            uiState = SearchResultUiState.Initial,
            navController = rememberNavController(),
            onClickReload = {},
            modifier = Modifier.mainContentPadding(PaddingValues(12.dp, 12.dp, 12.dp, 92.dp)),
            onError = { },
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4,
    showSystemUi = false
)
@Composable
fun ResultContent_Preview_Empty() {
    TemplateApp01Theme {
        ResultContent(
            uiState = SearchResultUiState.Initial,
            navController = rememberNavController(),
            onClickReload = {},
            modifier = Modifier.mainContentPadding(PaddingValues(12.dp, 12.dp, 12.dp, 92.dp)),
            onError = { },
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4,
    showSystemUi = false
)
@Composable
fun ResultContent_Preview_Success() {
    val photos = buildList {
        repeat(3) {
            add(
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
    }
    val navController = rememberNavController()
    TemplateApp01Theme {
        ResultContent(
            uiState = SearchResultUiState.Photos(photos),
            navController = navController,
            onClickReload = {},
            modifier = Modifier.mainContentPadding(PaddingValues(12.dp, 12.dp, 12.dp, 92.dp)),
            onError = { },
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4,
    showSystemUi = false
)
@Composable
fun ResultContent_Preview_Loading() {
    TemplateApp01Theme {
        ResultContent(
            uiState = SearchResultUiState.Loading,
            navController = rememberNavController(),
            onClickReload = {},
            modifier = Modifier.mainContentPadding(PaddingValues(12.dp, 12.dp, 12.dp, 92.dp)),
            onError = { },
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4,
    showSystemUi = false
)
@Composable
fun ResultContent_Preview_Error() {
    TemplateApp01Theme {
        ResultContent(
            uiState = SearchResultUiState.Error(result = ErrorResult.NetworkError("error error error")),
            navController = rememberNavController(),
            onClickReload = {},
            modifier = Modifier.mainContentPadding(PaddingValues(12.dp, 12.dp, 12.dp, 92.dp)),
            onError = { },
        )
    }
}