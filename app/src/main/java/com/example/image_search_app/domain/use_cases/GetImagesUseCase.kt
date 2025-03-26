package com.example.image_search_app.domain.use_cases

import com.example.image_search_app.domain.repository.ImageRepository

class GetImagesUseCase(
    private val imageRepository: ImageRepository,
) {

    //todo its not a compact function so how
    operator fun invoke(q: String) = imageRepository.getImages(q)

}