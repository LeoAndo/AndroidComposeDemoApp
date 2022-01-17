package com.example.templateapp01.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.templateapp01.model.UnSplashPhoto

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