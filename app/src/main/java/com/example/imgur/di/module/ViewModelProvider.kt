package com.example.imgur.di.module

import com.example.imgur.api.ApiServices
import com.example.imgur.ui.viewmodel.ImageDetailsViewModel
import com.example.imgur.ui.viewmodel.SearchViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelProvider {

    @Provides
    fun providesSearchViewModel(apiServices: ApiServices): SearchViewModel {
        return SearchViewModel(apiServices)
    }
    @Provides
    fun providesImageDetailsViewModel(apiServices: ApiServices): ImageDetailsViewModel {
        return ImageDetailsViewModel(apiServices)
    }

}

