package com.example.templateapp01.ui.components

import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.templateapp01.model.UnSplashPhoto
import com.example.templateapp01.ui.theme.TemplateApp01Theme

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

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4
)
@Composable
fun PhotoItemPreview() {
    val photo = UnSplashPhoto(
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
    TemplateApp01Theme {
        PhotoItem(
            photo = photo,
            onClick = {},
            modifier = Modifier.size(120.dp)
        )
    }
}