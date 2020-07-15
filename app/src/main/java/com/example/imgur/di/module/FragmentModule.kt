package com.example.imgur.di.module

import com.example.imgur.ui.fragment.ImageDetailFragment
import com.example.imgur.ui.fragment.SearchImageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector(modules = [ViewModelProvider::class])
    abstract fun contributeSearchImageFragment(): SearchImageFragment

    @ContributesAndroidInjector(modules = [ViewModelProvider::class])
    abstract fun contributeImageDetailFragment(): ImageDetailFragment

}