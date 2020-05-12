package com.yourself.flickrsearch.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourself.flickrsearch.data.Image
import com.yourself.flickrsearch.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageListViewModel : ViewModel() {
    private val TAG = ImageListViewModel::class.java.name

    @Inject
    lateinit var repository: ImageSearchRepository

    var imageListState = MutableLiveData<ApiResult<List<Image>>>()
    var totalPages: Int = 0

    init {
        imageListState.value =
            Success(emptyList(), false)
    }

    fun getImageListFor(searchTerm: String, pageNumber: Int = 1) {
        imageListState.value = Loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getImageListWith(searchTerm, pageNumber)
                withContext(Dispatchers.Main) {
                    if (result.userPost.pages.toInt() != 0) {
                        imageListState.value = Success(result.userPost.imageList, false)
                    } else {
                        imageListState.value = Error(null, false)
                    }
                    totalPages = result.userPost.pages.toInt()
                }
            } catch (exception: Exception) {
                Log.d(TAG, "Error from API ${exception.localizedMessage}")
                withContext(Dispatchers.Main) {
                    imageListState.value = Error(exception, false)
                }
            }
        }
    }
}