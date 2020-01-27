package com.example.android.searchdebounce.networking

import com.example.android.searchdebounce.data.PhotosSearchResponse
import retrofit2.http.GET

interface ApiService {
    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&text=dogs&api_key=$FLICKR_API_KEY")
    suspend fun fetchImages(): PhotosSearchResponse
}
