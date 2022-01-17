package com.example.templateapp01.data.repository.impl

import com.example.templateapp01.data.SafeResult
import com.example.templateapp01.data.api.UnsplashService
import com.example.templateapp01.data.apiCall
import com.example.templateapp01.data.repository.UnsplashRepository
import com.example.templateapp01.data.api.response.toModel
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
    ): SafeResult<List<UnSplashPhoto>> {
        return safeApiCall {
            api.searchPhotos(query, page, perPage).results.map { it.toModel() }
        }
    }

    override suspend fun searchPhotos2(
        query: String,
        page: Int,
        perPage: Int
    ): List<UnSplashPhoto> {
        return apiCall {
            api.searchPhotos(query, page, perPage).results.map { it.toModel() }
        }
    }
}