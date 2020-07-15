package com.example.imgur.ui.fragment

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imgur.R
import com.example.imgur.base.BaseFragment
import com.example.imgur.common.Common
import com.example.imgur.databinding.FragmentSearchViewLayoutBinding
import com.example.imgur.listeners.OnItemClickListener
import com.example.imgur.manager.Passable
import com.example.imgur.model.Image
import com.example.imgur.model.ImageData
import com.example.imgur.ui.adapter.SearchImagesAdapter
import com.example.imgur.ui.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search_view_layout.*
import javax.inject.Inject

class SearchImageFragment : BaseFragment<FragmentSearchViewLayoutBinding, SearchViewModel>(),
    OnItemClickListener<Image> {
    lateinit var list: ArrayList<ImageData>
    lateinit var adapter: SearchImagesAdapter
    override fun onReady() {
        binding = getViewDataBinding()
        list = ArrayList()
        binding.recyclerviewImages.layoutManager = GridLayoutManager(context, 2)
        adapter = SearchImagesAdapter(list, this)
        binding.recyclerviewImages.adapter = adapter
        setMainViewModelObserver()
        binding.edtSearchImage.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                vm.callSearchImage(edtSearchImage.text.toString())
                val imm: InputMethodManager =
                    v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                imm.hideSoftInputFromWindow(v.windowToken, 0)

            }
            false
        }
    }

    private fun setMainViewModelObserver() {
        vm.loading.observe(viewLifecycleOwner, Observer {
            if (it != null && it) {
                showLoader()
            }
        })
        vm.errorMessage.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                hideKeyBoard()
                hideLoader()
                showSnackBar(it)
            }
        })
        vm.searchImageObserver.observe(viewLifecycleOwner, Observer {
            hideLoader()
            if (it != null) {
                when (it.code) {
                    Common.SUCCESS -> {
                        list.clear()
                        it.data?.let { it1 -> list.addAll(it1) }

                        adapter.notifyDataSetChanged()
                    }
                    Common.UNAUTHENTICATED -> {


                    }
                    else -> {
                        showSnackBar("Error Occurred")
                    }
                }
            }
        })

    }

    lateinit var binding: FragmentSearchViewLayoutBinding

    override fun getViewModel(): SearchViewModel {
        return vm
    }

    @Inject
    lateinit var vm: SearchViewModel
    override fun getLayoutId(): Int {
        return R.layout.fragment_search_view_layout
    }

    override fun onItemClicked(t1: Image) {
        fragmentNavigationFactory.make(ImageDetailFragment()).hasData(object :
            Passable<ImageDetailFragment> {
            override fun passData(t: ImageDetailFragment) {
                t.setData(t1)
            }
        }).replace(true)
    }
}