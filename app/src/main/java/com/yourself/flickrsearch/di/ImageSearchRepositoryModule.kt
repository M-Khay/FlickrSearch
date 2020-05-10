package com.yourself.flickrsearch.di

import com.yourself.flickrsearch.repository.ImageSearchApi
import com.yourself.flickrsearch.repository.ImageSearchRepository
import com.yourself.flickrsearch.repository.ImageSearchRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageSearchRepositoryModule {
    @Provides
    @Singleton
    fun provideImageSearchRepositoryModule(imageSearchApi: ImageSearchApi):ImageSearchRepository = ImageSearchRepositoryImpl(imageSearchApi)
}