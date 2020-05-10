package com.yourself.flickrsearch.ui.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yourself.flickrsearch.data.Image
import com.yourself.flickrsearch.data.Photos
import com.yourself.flickrsearch.databinding.ImageListItemBinding
import com.yourself.flickrsearch.ui.ImageListViewModel


class ImageListAdapter :
    RecyclerView.Adapter<ImageListViewHolder>() {
    private var imageList = mutableListOf<Image>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        val teamListItemBinding =
            ImageListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageListViewHolder(teamListItemBinding.root, teamListItemBinding)
    }

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        holder.setData(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }


    fun updateImageList(imageList: List<Image>) {
        this.imageList.addAll(this.imageList.size, imageList.toMutableList())
        notifyItemRangeInserted(this.imageList.size - imageList.size, imageList.size)
    }

    fun clearLastSearchedItems() {
        this.imageList.clear()
        notifyDataSetChanged()
    }



}
