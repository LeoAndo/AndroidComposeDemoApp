package com.example.templateapp01.data.api

import com.example.templateapp01.data.response.UnsplashResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface UnsplashService {

    /*
    When the next page runs out, the response will be as follows
    {
    "total": 0,
    "total_pages": 0,
    "results":[]
    }
     */
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponse
}