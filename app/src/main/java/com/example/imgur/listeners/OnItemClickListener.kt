package com.example.imgur.listeners

import com.example.imgur.model.Image

interface OnItemClickListener<T> {
    fun onItemClicked(t: Image)

}