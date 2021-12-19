package com.example.templateapp01.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.templateapp01.data.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val repository: UnsplashRepository
) : ViewModel() {

    init {
        Log.d(LOG_TAG, "init: " + hashCode())
        viewModelScope.launch {
            repository.searchPhotos("dogs")
        }
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