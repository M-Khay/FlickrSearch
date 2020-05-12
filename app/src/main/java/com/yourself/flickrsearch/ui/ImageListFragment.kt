package com.yourself.flickrsearch.ui

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yourself.flickrsearch.R
import com.yourself.flickrsearch.data.Image
import com.yourself.flickrsearch.di.ComponentInjector
import com.yourself.flickrsearch.repository.ApiResult
import com.yourself.flickrsearch.repository.Error
import com.yourself.flickrsearch.repository.Loading
import com.yourself.flickrsearch.repository.Success
import com.yourself.flickrsearch.ui.rv.EndlessRecyclerViewScrollListener
import com.yourself.flickrsearch.ui.rv.ImageListAdapter
import com.yourself.searchyourcityweather.utils.NetworkConnectivity
import kotlinx.android.synthetic.main.fragment_image_list.*

class ImageListFragment : Fragment() {

    private lateinit var viewModel: ImageListViewModel
    private lateinit var adapter: ImageListAdapter
    private lateinit var onScrollListener: EndlessRecyclerViewScrollListener

    companion object {
        val TAG = ImageListFragment::class.java.name
        fun newInstance() = ImageListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ImageListViewModel::class.java).also {
            ComponentInjector.component.inject(it)
        }


        search_go.setOnClickListener {
            searchImages()
        }

        search_text.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                v.clearFocus()
                searchImages()
            }
            true
        }
        viewModel.imageListState.observe(this.viewLifecycleOwner, imageListObserver)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = ImageListAdapter()
        rv_image_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@ImageListFragment.adapter
        }

         onScrollListener = object :
            EndlessRecyclerViewScrollListener(rv_image_list.layoutManager as LinearLayoutManager?) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                // Triggered only when new data needs to be appended to the list
                searchMoreImages(page)
            }
        }
        rv_image_list.addOnScrollListener(onScrollListener)

        rv_image_list.addItemDecoration(
            DividerItemDecoration(
                rv_image_list.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }


    private fun searchImages() {
        hideKeyboard()
        clearPreviousSearchedData()
        adapter.clearLastSearchedItems()
        val searchText = search_text.text.toString()
        if (TextUtils.isEmpty(searchText)) {
            input_layout.error = resources.getString(R.string.empty_team_name)
            Toast.makeText(activity, "Search Term Cannot be empty", Toast.LENGTH_LONG).show()
        } else {
            input_layout.error = ""
            if (NetworkConnectivity.isNetworkConnected) {
                viewModel.getImageListFor(searchText)
                Log.d(TAG, "Searched Team : $searchText")
            } else {
                showAlertDialog(
                    resources.getString(R.string.network_error_title),
                    resources.getString(R.string.network_error_message),
                    resources.getString(R.string.alert_dialog_ok)
                )
            }
        }
    }

    private fun clearPreviousSearchedData() {
        adapter.clearLastSearchedItems()
        onScrollListener.resetState()
    }

    private fun searchMoreImages(pageNumber: Int) {
        if (pageNumber < viewModel.totalPages) {
            val searchText = search_text.text.toString()
            if (NetworkConnectivity.isNetworkConnected) {
                viewModel.getImageListFor(searchText, pageNumber)
            } else {
                showAlertDialog(
                    resources.getString(R.string.network_error_title),
                    resources.getString(R.string.network_error_message),
                    resources.getString(R.string.alert_dialog_ok)
                )
            }
        }
    }

    private val imageListObserver = Observer<ApiResult<List<Image>>> { state ->
        when (state) {
            is Success<List<Image>> -> {
                loading_content.visibility = View.GONE
                rv_image_list.visibility = View.VISIBLE
                adapter.updateImageList(state.data)
            }
            is Loading -> {
                loading_content.visibility = View.VISIBLE
            }
            is Error -> {
                loading_content.visibility = View.GONE
                input_layout.error = resources.getString(R.string.invalid_team_name)
            }
        }
    }

    private fun showAlertDialog(title: String, message: String, positiveButtonText: String?) {
        MaterialAlertDialogBuilder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun hideKeyboard() {
        val keyboard =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    fun gotoListTop() {
        rv_image_list.smoothScrollToPosition(0)
    }

}
