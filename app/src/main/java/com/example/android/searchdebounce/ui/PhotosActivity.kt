package com.example.android.searchdebounce.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android.searchdebounce.R
import kotlinx.android.synthetic.main.activity_photos.photosRecyclerView
import kotlinx.android.synthetic.main.activity_photos.searchBox
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SEARCH_DELAY_MS = 200L

class PhotosActivity : AppCompatActivity() {
    private val photosViewModel: PhotosViewModel by viewModels()
    private val photosAdapter = PhotosAdapter()

    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)

        // Set up search box
        searchBox.addTextChangedListener { editable ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(SEARCH_DELAY_MS)
                val imagesList = photosViewModel.fetchImages(editable.toString())
                with(photosAdapter) {
                    photos.clear()
                    photos.addAll(imagesList)
                    notifyDataSetChanged()
                }
            }
        }
        // Set up recycler view
        photosRecyclerView.adapter = photosAdapter
        photosRecyclerView.layoutManager = GridLayoutManager(this, 3)
    }
}
