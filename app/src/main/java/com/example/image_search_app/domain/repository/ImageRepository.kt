package com.example.image_search_app.domain.repository

import androidx.paging.Pager
import com.example.image_search_app.domain.model.Image


// ? why Pager is return type
interface ImageRepository {
    fun getImages(q: String): Pager<Int, Image>
}