package com.example.templateapp01.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.templateapp01.ui.theme.TemplateApp01Theme

/**
 * Full screen circular progress indicator
 */
@Composable
internal fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Preview("FullScreenLoading")
@Preview("FullScreenLoading (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSearchScreen() {
    TemplateApp01Theme {
        Surface {
            FullScreenLoading()
        }
    }
}