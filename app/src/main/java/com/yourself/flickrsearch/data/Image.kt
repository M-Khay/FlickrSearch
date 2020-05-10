package com.yourself.flickrsearch.data

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName

data class Image(var title: String, var secret: String, var server: String, @SerializedName("farm") var host: Int, var id: String){
    companion object {
        @JvmStatic
        @BindingAdapter("imageURL")
        fun loadImage(imageView: ImageView, imageUrl: String) {
            Glide.with(imageView.context)
                .setDefaultRequestOptions(
                    RequestOptions()
                        .fitCenter()
                )
                .load(imageUrl)
                .into(imageView)
        }
    }
}