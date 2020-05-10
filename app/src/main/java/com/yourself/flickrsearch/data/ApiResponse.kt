package com.yourself.flickrsearch.data

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("photos")
    var photos: Photos
)