package com.example.templateapp01.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.templateapp01.ui.components.NavigateBackButton

@Composable
internal fun ResultScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController,
    id: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Hello, $id")
        Spacer(modifier = Modifier.height(8.dp))
        NavigateBackButton(navController = navController)
    }
}