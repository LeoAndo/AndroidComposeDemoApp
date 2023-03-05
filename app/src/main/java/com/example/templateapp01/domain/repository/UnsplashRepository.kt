package com.example.templateapp01.domain.repository

import com.example.templateapp01.data.SafeResult
import com.example.templateapp01.domain.model.UnSplashPhoto

internal interface UnsplashRepository {
    suspend fun searchPhotos(
        query: String,
        page: Int = 1,
        perPage: Int = 10
    ): SafeResult<List<UnSplashPhoto>>
}