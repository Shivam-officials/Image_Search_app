package com.example.image_search_app.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.image_search_app.data.Mappers.ImageDTOtoImageMapper
import com.example.image_search_app.data.pagingSource.ImagePagingSource
import com.example.image_search_app.data.remote.ApiService
import com.example.image_search_app.domain.model.Image
import com.example.image_search_app.domain.repository.ImageRepository
import javax.inject.Inject


class ImageRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: ImageDTOtoImageMapper
) : ImageRepository {
    override fun getImages(q: String): Pager<Int, Image> {
        return Pager<Int, Image>(
            config = PagingConfig(
                initialLoadSize = 20,
                prefetchDistance = 5,
                pageSize = 10,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                ImagePagingSource(
                    apiService = apiService,
                    mapper = mapper,
                    q = q
                )
            }

            )
    }
}