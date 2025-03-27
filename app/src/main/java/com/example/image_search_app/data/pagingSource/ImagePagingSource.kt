package com.example.image_search_app.data.pagingSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.image_search_app.data.Mappers.ImageDTOtoImageMapper
import com.example.image_search_app.data.Mappers.mapAll
import com.example.image_search_app.data.remote.ApiService
import com.example.image_search_app.domain.model.Image
import kotlinx.coroutines.delay

class ImagePagingSource(
    private val apiService: ApiService,
    private val q: String,
    private val mapper: ImageDTOtoImageMapper,
) : PagingSource<Int, Image>() {

    /**
     * Determines the refresh key for PagingSource to reload the current page on refresh.
     *
     * @param state The current PagingState with loaded pages and anchor position `(current position of the user's scroll)`.
     * @return The page number to reload, or null to start from the beginning.
     *
     * Logic:
     * 1. Find the closest loaded page to the anchor position.
     * 2. Return `prevKey + 1` if available, else `nextKey - 1`.
     * 3. If no suitable key is found, return `null`.
     */
    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {

        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    // this is responsible for fetching data when needed.Whenever the user scrolls
    // or refreshes, the Paging library calls load() to get the next set of data.
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val page = params.key ?: 1 //page number
        val pageSize = params.loadSize

        // call the data
        return try {
            val images = apiService.getImages(q = q, page = page) // Fetch images for the given page
            Log.d ("Taggg",images.toString())
//            delay(3000)
            LoadResult.Page(
                data = mapper.mapAll(images.hits), // Convert API data to UI model

                prevKey = if (page == 1) null else page - 1, // No previous page if it's the first page

                nextKey = if (images.hits.size < pageSize) null else page + 1
                // If `hits.size < pageSize`, it means this is the last page, so `nextKey = null`

                /* Example:
                    - Assume pageSize = 30
                    - API returns:

                      Page 1 -> hits.size = 30  -> prevKey = null, nextKey = 2  (More pages available)
                      Page 2 -> hits.size = 30  -> prevKey = 1, nextKey = 3  (More pages available)
                      Page 3 -> hits.size = 10  -> prevKey = 2, nextKey = null  (Last page, stop pagination)
                */
            )

        } catch (e: Exception) {
            LoadResult.Error<Int, Image>(e)
        }

    }
}