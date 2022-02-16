package com.example.templateapp01.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.templateapp01.domain.repository.UnsplashRepository

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val repository: UnsplashRepository
) : ViewModel() {

    var uiState by mutableStateOf<HomeUiState>(HomeUiState.Initial)
        private set

    init {
        Log.d(LOG_TAG, "init: " + hashCode())
        searchPhotos()
    }

    fun searchPhotos() {
        uiState = HomeUiState.Loading // start loading.

        viewModelScope.launch {
            repository.searchPhotos("dogs").fold(
                onSuccess = { data ->
                    uiState = if (data.isEmpty()) {
                        HomeUiState.NoPhotos // stop loading.
                    } else {
                        HomeUiState.Photos(results = data) // stop loading.
                    }
                }, onFailure = {
                    uiState = HomeUiState.Failure(it) // stop loading.
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(LOG_TAG, "onCleared" + hashCode())
    }

    companion object {
        const val LOG_TAG = "HomeViewModel"
    }
}