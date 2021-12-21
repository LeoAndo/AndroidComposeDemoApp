package com.example.templateapp01.ui.search

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.templateapp01.data.ErrorResult
import com.example.templateapp01.data.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchResultViewModel @Inject constructor(
    private val repository: UnsplashRepository
) : ViewModel() {

    var uiState = mutableStateOf<UiState>(UiState.NoPhotos)
        private set

    init {
        Log.d(LOG_TAG, "init: " + hashCode())
    }

    fun searchPhotos(query: String) {
        Log.d(LOG_TAG, "searchPhotos: $query")
        uiState.value = UiState.Loading // start loading.
        viewModelScope.launch {
            kotlin.runCatching {
                repository.searchPhotos2(query)
            }.onSuccess {
                // success
                uiState.value = UiState.Photos(results = it)
            }.onFailure {
                // error
                if (it is ErrorResult) {
                    uiState.value = UiState.Error(it)
                } else {
                    uiState.value = UiState.OtherError(it.localizedMessage ?: "")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(LOG_TAG, "onCleared" + hashCode())
    }

    companion object {
        const val LOG_TAG = "SearchResultViewModel"
    }
}