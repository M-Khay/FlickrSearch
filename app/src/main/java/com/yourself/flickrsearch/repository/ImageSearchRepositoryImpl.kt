package com.yourself.flickrsearch.repository

import com.yourself.flickrsearch.data.ApiResponse
import com.yourself.flickrsearch.utils.Constant.API_KEY
import com.yourself.flickrsearch.utils.Constant.FORMAT
import com.yourself.flickrsearch.utils.Constant.METHOD
import com.yourself.flickrsearch.utils.Constant.NO_JSON_CALLBACK

class ImageSearchRepositoryImpl(private val imageSearchApi: ImageSearchApi) :
    ImageSearchRepository {
    override suspend fun getImageListWith(searchTerm: String, pageNumber: Int): ApiResponse =
        imageSearchApi.getImageListFor(METHOD, API_KEY, 10, NO_JSON_CALLBACK,  FORMAT,pageNumber, searchTerm)

}