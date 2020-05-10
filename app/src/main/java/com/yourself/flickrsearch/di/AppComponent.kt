package com.yourself.searchyourcityweather.di

import com.yourself.flickrsearch.di.AppModule
import com.yourself.flickrsearch.di.ImageSearchRepositoryModule
import com.yourself.flickrsearch.di.NetworkModule
import com.yourself.flickrsearch.ui.ImageListViewModel
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, AppModule::class,
        NetworkModule::class, ImageSearchRepositoryModule::class]
)

interface AppComponent {
    fun inject(imageListViewModel: ImageListViewModel)
}


