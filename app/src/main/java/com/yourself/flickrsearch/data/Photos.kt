package com.yourself.flickrsearch.data

import com.google.gson.annotations.SerializedName

data class Photos( var pages: String,
                   var page: Int,
                   @SerializedName("photo")
                   var imageList : List<Image>)