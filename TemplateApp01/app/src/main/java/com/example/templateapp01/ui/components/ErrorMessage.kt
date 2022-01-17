package com.example.templateapp01.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.templateapp01.ui.theme.TemplateApp01Theme

@Composable
fun ErrorMessage(
    message: String,
    onClickReload: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = message)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onClickReload) {
            Text("reload")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorMessagePreview() {
    TemplateApp01Theme {
        ErrorMessage(
            message = "Could not load.",
            onClickReload = {},
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
        )
    }
}