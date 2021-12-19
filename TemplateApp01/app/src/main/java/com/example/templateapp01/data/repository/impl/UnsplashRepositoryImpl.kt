package com.example.templateapp01.data.repository.impl

import com.example.templateapp01.data.Result
import com.example.templateapp01.data.api.UnsplashService
import com.example.templateapp01.data.repository.UnsplashRepository
import com.example.templateapp01.data.response.toModel
import com.example.templateapp01.data.safeApiCall
import com.example.templateapp01.model.UnSplashPhoto
import javax.inject.Inject

internal class UnsplashRepositoryImpl @Inject constructor(
    private val api: UnsplashService
) : UnsplashRepository {
    override suspend fun searchPhotos(
        query: String,
        page: Int,
        perPage: Int
    ): Result<List<UnSplashPhoto>> {
        return safeApiCall {
            api.searchPhotos(query, page, perPage).results.map { it.toModel() }
        }
    }
}