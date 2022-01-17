package com.example.templateapp01.data.repository

import com.example.templateapp01.data.SafeResult
import com.example.templateapp01.model.UnSplashPhoto

internal interface UnsplashRepository {
    suspend fun searchPhotos(
        query: String,
        page: Int = 1,
        perPage: Int = 10
    ): SafeResult<List<UnSplashPhoto>>
}