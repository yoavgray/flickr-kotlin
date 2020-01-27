package com.example.android.searchdebounce.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.searchdebounce.domain.Photo
import com.example.android.searchdebounce.networking.WebClient
import kotlinx.coroutines.launch

class PhotosViewModel : ViewModel() {
    private val mutablePhotosListLiveData = MutableLiveData<List<Photo>>()
    private val photosListLiveData: LiveData<List<Photo>> = mutablePhotosListLiveData

    var photosAdapter = PhotosAdapter()

    fun loadPhotos(): LiveData<List<Photo>> {
        viewModelScope.launch {
            val searchResponse = WebClient.client.fetchImages()
            val photosList = searchResponse.photos.photo.map { photo ->
                Photo(
                    id = photo.id,
                    url = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg",
                    title = photo.title
                )
            }
            mutablePhotosListLiveData.postValue(photosList)
        }
        return photosListLiveData
    }
}
