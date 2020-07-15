package com.example.imgur.api

import com.google.gson.annotations.SerializedName


/**
 * Created by Soham Robinkumar Sheth on 11/6/18.
 */
data class AppResponse<T>(
    @field:SerializedName("status")
    val code: Int = 0,
    @field:SerializedName("success")
    val success: Boolean? = null,
    @field:SerializedName("data")
    val data: T? = null
)