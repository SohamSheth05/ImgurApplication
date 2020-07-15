package com.example.imgur.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.example.imgur.application.ImgUrApp
import com.example.imgur.di.module.ActivityModule
import com.example.imgur.di.module.AppModule
import com.google.gson.Gson
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun provideResources(): Resources

    fun provideGson(): Gson

    fun provideContext(): Context




    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(imgUrApp: ImgUrApp)
}