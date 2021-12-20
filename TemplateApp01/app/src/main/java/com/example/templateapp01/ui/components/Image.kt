package com.example.templateapp01.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.templateapp01.R

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PhotoImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    MyAppSurface(
        color = Color.LightGray,
        elevation = elevation,
        shape = CircleShape,
        modifier = modifier,
    ) {
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = {
                    crossfade(true)
                    placeholder(drawableResId = R.drawable.placeholder)
                }
            ),
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}