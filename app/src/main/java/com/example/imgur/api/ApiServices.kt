package com.example.imgur.api


import com.example.imgur.model.ImageData
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*


interface ApiServices {

    @GET(URLFactory.SEARCH_KEYWORD)
    fun getImagesList(
        @Query("q") path_id: String
    ): Single<AppResponse<List<ImageData>>>


}