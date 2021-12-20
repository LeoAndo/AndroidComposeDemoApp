package com.example.templateapp01.ui.search

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.templateapp01.ui.components.FullScreenLoading
import com.example.templateapp01.ui.components.NavigateBackButton
import com.example.templateapp01.ui.components.NavigateBackIconButton
import com.example.templateapp01.ui.home.PhotoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResultScreen(
    viewModel: SearchResultViewModel = hiltViewModel(),
    navController: NavHostController,
    query: String
) {
    val uiState by remember { viewModel.uiState }
    val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }

    Log.d("ResultScreen", "uiState: $uiState")
    Log.d("ResultScreen", "scrollBehavior: $scrollBehavior")

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "ResultScreen")
                },
                navigationIcon = {
                    NavigateBackIconButton(navController = navController)
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            when (val ret = uiState) {
                UiState.Error -> {
                    Text(text = "fetch error.")
                }
                UiState.Loading -> {
                    FullScreenLoading()
                }
                UiState.NoPhotos -> {
                    Text(text = "empty.")
                    viewModel.searchPhotos(query)
                }
                is UiState.Photos -> {
                    PhotoItem(
                        photo = ret.results.last(),
                        onClick = {
                            Log.d("ResultScreen", "PhotoItem Clicked! id: $it")
                        },
                        modifier = Modifier
                            .size(120.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            NavigateBackButton(navController = navController, modifier = Modifier.fillMaxWidth())
        }
    }
}