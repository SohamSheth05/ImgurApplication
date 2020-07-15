package com.example.imgur.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imgur.api.ApiServices
import com.example.imgur.api.AppResponse
import com.example.imgur.model.ImageData

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import java.net.UnknownHostException
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val apiServices: ApiServices

) : ViewModel() {

    var loading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()
    var errorThrowable = MutableLiveData<Throwable>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    var searchImageObserver = MutableLiveData<AppResponse<List<ImageData>>>()
    fun callSearchImage(query:String){
        loading.value = true
        compositeDisposable.add(
            apiServices.getImagesList(query)
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { user ->

                        loading.value = false
                        searchImageObserver.value = user
                    },
                    { throwable ->
                        loading.value = false
                        errorThrowable.value=throwable
                        if (throwable is UnknownHostException) {
                            errorMessage.value = throwable.localizedMessage
                        } else {
                            errorMessage.value = throwable.localizedMessage
                        }
                    })
        )
    }
}
