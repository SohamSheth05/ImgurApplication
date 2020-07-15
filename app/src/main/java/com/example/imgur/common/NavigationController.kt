package com.example.imgur.common

import com.example.imgur.base.BaseActivity


class NavigationController(private val baseActivity: BaseActivity<*, *>) {

    companion object {
        const val KEY_MESSAGE = "message"
        const val REQUEST_CODE_WITH_MESSAGE = 12345
    }


    fun goBack() {
        baseActivity.onBackPressed()
    }



}