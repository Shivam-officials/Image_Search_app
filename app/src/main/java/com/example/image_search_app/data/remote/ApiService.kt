package com.example.image_search_app.data.remote

import com.example.image_search_app.data.model.remote.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    //https://pixabay.com/api/?key=49506276-bf2dcfeeeae3880abb26f73f5&q=yellow+flowers&image_type=photo&pretty=true
    @GET("api/")
    suspend fun getImages(
        @Query("key") apiKey:String = "49506276-bf2dcfeeeae3880abb26f73f5",
        @Query("q") q:String,
        @Query("page") page: Int
    ): ImageResponse
}