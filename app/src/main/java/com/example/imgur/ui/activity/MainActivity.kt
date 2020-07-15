package com.example.imgur.ui.activity

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.imgur.BR
import com.example.imgur.R
import com.example.imgur.base.BaseActivity
import com.example.imgur.base.BaseFragment
import com.example.imgur.databinding.ActivityMainBinding
import com.example.imgur.ui.fragment.SearchImageFragment
import com.example.imgur.ui.viewmodel.SearchViewModel
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, SearchViewModel>() {
    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): SearchViewModel {
        return vm
    }

    override fun onBackPressed() {

        if (getCurrentFragment<BaseFragment<*, *>>() is SearchImageFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    @Inject
    lateinit var vm: SearchViewModel

    override fun findFragmentPlaceHolder(): Int {
        return R.id.container
    }

    override fun onReady(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            fragmentNavigationFactory.make(SearchImageFragment()).add(true)
        }
    }

    override fun findContentView(): Int {
        return R.layout.activity_main
    }

}