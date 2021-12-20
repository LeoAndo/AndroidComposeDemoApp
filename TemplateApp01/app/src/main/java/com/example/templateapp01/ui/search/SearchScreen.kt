package com.example.templateapp01.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.templateapp01.ui.theme.TemplateApp01Theme

@Composable
internal fun SearchScreen(
    navigateToNextScreen: (String) -> Unit
) {
    var queryText by remember { mutableStateOf("") }
    var isEnableBtn by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = queryText,
            onValueChange = {
                queryText = it
                isEnableBtn = it.isNotEmpty()
            },
            label = { Text(text = "query word") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                navigateToNextScreen(queryText)
            }, modifier = Modifier.fillMaxWidth(),
            enabled = isEnableBtn
        ) {
            Text(text = "to Result Screen.")
        }
    }
}

@Preview("SearchScreen")
@Preview("SearchScreen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSearchScreen() {
    TemplateApp01Theme {
        Surface {
            SearchScreen(navigateToNextScreen = {})
        }
    }
}