package com.yourself.flickrsearch.ui.rv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yourself.flickrsearch.data.Image
import com.yourself.flickrsearch.databinding.ImageListItemBinding

class ImageListViewHolder constructor(teamListItemView: View, private val imageListItemBinding: ImageListItemBinding) :
    RecyclerView.ViewHolder(teamListItemView) {

    fun setData(image: Image) {
        imageListItemBinding.image= image
    }

}