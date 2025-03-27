package com.example.image_search_app.data.DI

import com.example.image_search_app.data.Mappers.ImageDTOtoImageMapper
import com.example.image_search_app.data.remote.ApiService
import com.example.image_search_app.data.repository.ImageRepoImpl
import com.example.image_search_app.data.repository.ImageRepoImpl_Factory
import com.example.image_search_app.domain.model.Image
import com.example.image_search_app.domain.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DataModule {
//https://pixabay.com/api/?key=49506276-bf2dcfeeeae3880abb26f73f5&q=yellow+flowers&image_type=photo&pretty=true
    @Provides
    @Singleton
    fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://pixabay.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

   @Provides
   @Singleton
   fun provideApiService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
   }

    @Provides
    @Singleton
    fun provideImageRepo(apiService: ApiService,mapper: ImageDTOtoImageMapper): ImageRepository{
        return ImageRepoImpl(apiService, mapper )
    }

}