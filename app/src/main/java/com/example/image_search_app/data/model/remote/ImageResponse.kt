package com.example.image_search_app.data.model.remote

data class ImageResponse(
    val hits: List<ImageDTO>,
    val total: Int,
    val totalHits: Int
)