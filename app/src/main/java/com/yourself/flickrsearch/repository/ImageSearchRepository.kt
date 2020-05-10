package com.yourself.flickrsearch.repository

import com.yourself.flickrsearch.data.ApiResponse

interface ImageSearchRepository {
    suspend fun getImageListWith(name : String, pageNumber : Int): ApiResponse
}