package com.yourself.flickrsearch.repository

import com.yourself.flickrsearch.data.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ImageSearchApi {
    @GET("services/rest/")

    suspend fun getImageListFor(
        @Query("method") methodName: String,
        @Query("api_key") apiKey: String,
        @Query("per_page") perPage: Int,
        @Query("nojsoncallback") value: Int,
        @Query("format") format: String,
        @Query("page") pageNumber: Int,
        @Query("text") searchTerm: String
    ): ApiResponse

}