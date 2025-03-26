package com.example.image_search_app.data.Mappers

import com.example.image_search_app.data.model.remote.ImageDTO
import com.example.image_search_app.domain.model.Image
import javax.inject.Inject


// inject ctor just add this ctor is attached to our dependency graph so that its very easy to inject this mapper in our pagingsource class
class ImageDTOtoImageMapper @Inject constructor(): Mapper<ImageDTO, Image> {
    override fun map(from: ImageDTO): Image {
        return Image(
            id = from.id.toString(),
            imageUrl = from.largeImageURL,
        )
    }
}