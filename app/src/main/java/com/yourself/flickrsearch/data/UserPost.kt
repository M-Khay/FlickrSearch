package com.yourself.flickrsearch.data

import com.google.gson.annotations.SerializedName

data class UserPost(var pages: String,
                    var page: Int,
                    @SerializedName("photo")
                   var imageList : List<Image>)