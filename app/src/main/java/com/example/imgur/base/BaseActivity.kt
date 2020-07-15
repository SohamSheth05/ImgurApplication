package com.example.imgur.base


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.admin.DevicePolicyManager
import android.app.admin.SystemUpdatePolicy
import android.content.*
import android.net.ConnectivityManager
import android.os.*
import android.provider.Settings
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.example.imgur.R
import com.example.imgur.api.ApiServices
import com.example.imgur.common.NavigationController
import com.example.imgur.manager.FragmentNavigationFactory
import com.example.imgur.utils.AlertUtils

import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

import com.google.gson.Gson
import dagger.android.support.DaggerAppCompatActivity

import java.util.*
import javax.inject.Inject


abstract class BaseActivity<T : ViewDataBinding, V : ViewModel> : DaggerAppCompatActivity(),
    RootView {


    @Inject
    lateinit var apiServices: ApiServices

    @Inject
    lateinit var gson: Gson
    protected var toolbar: Toolbar? = null
    private var appBarLayout: AppBarLayout? = null
    private var toolbarTitle: AppCompatTextView? = null
    lateinit var navigationController: NavigationController
    lateinit var fragmentNavigationFactory: FragmentNavigationFactory
    var isBack = false

    private var materialAlertDialog: AlertDialog? = null


    private lateinit var mViewDataBinding: T
    private var mViewModel: V? = null

    var outJsonKey: String = ""
    lateinit var apk: String
    override fun onCreate(savedInstanceState: Bundle?) {
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())
        super.onCreate(savedInstanceState)
//
        mViewDataBinding = DataBindingUtil.setContentView(this, findContentView())
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        navigationController = NavigationController(this)
        fragmentNavigationFactory = FragmentNavigationFactory(this)


        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
        onReady(savedInstanceState)
        hideStatusBar()
    }

    private fun hideStatusBar() { // Hide status bar
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private val blockedKeys: List<*> =
        ArrayList(
            listOf(
                KeyEvent.KEYCODE_VOLUME_DOWN,
                KeyEvent.KEYCODE_VOLUME_UP,
                KeyEvent.KEYCODE_HOME,
                KeyEvent.KEYCODE_BACK
            )
        )


    abstract fun getBindingVariable(): Int
    fun getViewDataBinding(): T {
        return mViewDataBinding
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V


    abstract fun onReady(savedInstanceState: Bundle?)

    fun setAnimation() {
        this.overridePendingTransition(R.anim.anim_left, R.anim.anim_right)
    }


    fun <F : BaseFragment<*, *>> getCurrentFragment(): androidx.fragment.app.Fragment? {
        return if (findFragmentPlaceHolder() == 0) null else supportFragmentManager.findFragmentById(
            findFragmentPlaceHolder()
        )
    }

    abstract fun findFragmentPlaceHolder(): Int

    @LayoutRes
    abstract fun findContentView(): Int

    override fun showSnackBar(message: String) {
        showSnackBar(message, false)
    }

    override fun showSnackBar(message: String, showOk: Boolean) {
        showSnackBar(findViewById(R.id.container), message, showOk)
    }

    override fun showSnackBar(view: View, message: String, showOk: Boolean) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackBar.setActionTextColor(ContextCompat.getColor(this, android.R.color.white))

        val sbView = snackBar.view
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        val textView = sbView.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white))

        if (showOk)
            snackBar.setAction("Ok") { snackBar.dismiss() }

        snackBar.show()
    }

    override fun showLoader() {
        AlertUtils.showCustomProgressDialog(this)
    }

    override fun hideLoader() {
        AlertUtils.dismissDialog()
    }


    override fun showKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    override fun hideKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    override fun showDialogWithOneAction(
        title: String?, message: String?,
        positiveButton: String?,
        positiveFunction: (DialogInterface, Int) -> Unit
    ) {
        showDialogWithTwoActions(title, message, positiveButton, null, positiveFunction) { _, _ -> }
    }

    override fun showDialogWithTwoActions(
        title: String?, message: String?,
        positiveName: String?, negativeName: String?,
        positiveFunction: (DialogInterface, Int) -> Unit,
        negativeFunction: (DialogInterface, Int) -> Unit
    ) {

        materialAlertDialog?.dismiss()
        materialAlertDialog = MaterialAlertDialogBuilder(this).setCancelable(false).setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveName, positiveFunction)
            .setNegativeButton(negativeName, negativeFunction).create()
        materialAlertDialog?.show()

        val textView = materialAlertDialog?.findViewById<TextView>(android.R.id.message)
    }

    override fun hideDialog() {
        if (materialAlertDialog?.isShowing!!) {
            materialAlertDialog?.dismiss()
        }
    }


}