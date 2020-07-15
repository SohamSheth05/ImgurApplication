package com.example.imgur.manager

import android.content.Context
import com.example.imgur.R
import com.example.imgur.base.BaseActivity
import com.example.imgur.base.BaseFragment


object FragmentFactory {

    fun <T : BaseFragment<*, *>> getFragment(aClass: Class<T>): T? {

        try {
            return aClass.newInstance()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        return null
    }

    fun <F : BaseFragment<*, *>> getCurrentFragment(context: Context): F? {
        return if (findFragmentPlaceHolder() == 0) null else (context as BaseActivity<*, *>).supportFragmentManager.findFragmentById(
            findFragmentPlaceHolder()
        ) as F?
    }

    fun findFragmentPlaceHolder(): Int {
        return R.id.container
    }
}
