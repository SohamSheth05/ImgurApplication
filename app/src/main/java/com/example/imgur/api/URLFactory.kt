package com.example.imgur.api

import okhttp3.HttpUrl


object URLFactory {
    /*https://api.imgur.com/3/gallery/search/1?q=vanilla*/
    lateinit var SCHEME: String
    lateinit var HOST: String
    lateinit var API_PATH: String
    fun provideHttpUrl(): HttpUrl {
        SCHEME = "https"
        HOST = "api.imgur.com"
        API_PATH = "3/gallery/"
        return HttpUrl.Builder()
            .scheme(SCHEME)
            .host(HOST)
            .addPathSegments(API_PATH)
            .build()


    }

    const val SEARCH_KEYWORD = "search/"
}
