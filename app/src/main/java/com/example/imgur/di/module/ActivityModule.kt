package com.example.imgur.di.module


import com.example.imgur.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeAuthenticationActivity(): MainActivity



}