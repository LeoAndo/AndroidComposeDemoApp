package com.example.templateapp01.model

import java.io.Serializable

data class UnSplashPhoto(
    val id: String,
    val urls: UnSplashPhotoUrls,
    val likes: Int,
    val user: UnSplashUser,
) : Serializable {
    data class UnSplashPhotoUrls(
        val full: String,
        val regular: String,
    )

    data class UnSplashUser(
        val username: String,
        val profileImage: ProfileImage
    ) {
        data class ProfileImage(val small: String?)
    }
}