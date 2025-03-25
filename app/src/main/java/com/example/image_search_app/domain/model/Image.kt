package com.example.image_search_app.domain.model

import java.util.UUID

data class Image(
    val id: String,
    val imageUrl:String,
    val uuid: String = UUID.randomUUID().toString()
)
