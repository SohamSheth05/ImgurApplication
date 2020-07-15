package com.example.imgur.application

import com.example.imgur.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class ImgUrApp : DaggerApplication(){
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val application = DaggerAppComponent.builder().application(this).build()
        application.inject(this)
        return application
    }

}