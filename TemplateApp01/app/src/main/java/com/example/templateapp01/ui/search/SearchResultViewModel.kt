package com.example.templateapp01.ui.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.templateapp01.data.ErrorResult
import com.example.templateapp01.data.SafeResult
import com.example.templateapp01.domain.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchResultViewModel @Inject constructor(
    private val repository: UnsplashRepository
) : ViewModel() {

    var uiState by mutableStateOf<SearchResultUiState>(SearchResultUiState.Initial)
        private set

    init {
        Log.d(LOG_TAG, "init: " + hashCode())
        uiState = SearchResultUiState.SearchPhotos
    }

    fun searchPhotos(query: String) {
        Log.d(LOG_TAG, "searchPhotos: $query")
        uiState = SearchResultUiState.Loading // start loading.

        viewModelScope.launch {
            when (val ret = repository.searchPhotos(query)) {
                is SafeResult.Success -> {
                    uiState = if (ret.data.isEmpty()) {
                        SearchResultUiState.NoPhotos
                    } else {
                        SearchResultUiState.Photos(results = ret.data)
                    }
                }
                is SafeResult.Error -> {
                    when (ret.errorResult) {
                        is ErrorResult.BadRequestError, is ErrorResult.NetworkError,
                        is ErrorResult.NotFoundError, is ErrorResult.OtherError, is ErrorResult.UnAuthorizedError -> {
                            uiState = SearchResultUiState.Error(ret.errorResult) // stop loading.
                        }
                    }
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