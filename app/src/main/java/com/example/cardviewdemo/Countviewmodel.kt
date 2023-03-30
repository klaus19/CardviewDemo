package com.example.cardviewdemo

import androidx.lifecycle.ViewModel

class Countviewmodel:ViewModel() {


    var count = 0
    fun addWordCount(){
        count++
    }

    override fun onCleared() {
        super.onCleared()
    }
}