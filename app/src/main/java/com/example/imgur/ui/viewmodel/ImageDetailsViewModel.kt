package com.example.imgur.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.imgur.api.ApiServices
import javax.inject.Inject

class ImageDetailsViewModel @Inject constructor(var apiServices: ApiServices) : ViewModel() {

}