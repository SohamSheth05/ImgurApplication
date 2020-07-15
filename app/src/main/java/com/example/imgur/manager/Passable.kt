package com.example.imgur.manager

interface Passable<in T> {

    fun passData(t: T)

}
