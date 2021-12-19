package com.example.templateapp01.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.templateapp01.ui.theme.TemplateApp01Theme

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToNextScreen: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var isEnableBtn by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box {
            BasicTextField(
                value = text,
                modifier = Modifier
                    .fillMaxWidth(),
                onValueChange = {
                    text = it
                    isEnableBtn = it.isNotEmpty()
                },
            )
            if (text.isEmpty()) {
                Text(text = "input pokemon id..", fontSize = 20.sp, color = Color.LightGray)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                navigateToNextScreen(text)
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