package com.example.templateapp01.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor() : ViewModel() {

    init {
        Log.d(LOG_TAG, "init: " + hashCode())
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(LOG_TAG, "onCleared" + hashCode())
    }

    fun print() {
        Log.d(LOG_TAG, "print!" + hashCode())
    }

    companion object {
        const val LOG_TAG = "HomeViewModel"
    }
}