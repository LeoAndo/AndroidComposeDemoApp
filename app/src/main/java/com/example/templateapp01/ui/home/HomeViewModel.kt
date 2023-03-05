package com.example.templateapp01.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.templateapp01.data.ErrorResult
import com.example.templateapp01.data.SafeResult
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
            when (val ret = repository.searchPhotos("dogs")) {
                is SafeResult.Success -> {
                    uiState = if (ret.data.isEmpty()) {
                        HomeUiState.NoPhotos // stop loading.
                    } else {
                        HomeUiState.Photos(results = ret.data) // stop loading.
                    }
                }
                is SafeResult.Error -> {
                    when (ret.errorResult) {
                        is ErrorResult.BadRequestError, is ErrorResult.NetworkError,
                        is ErrorResult.NotFoundError, is ErrorResult.UnexpectedError, is ErrorResult.UnAuthorizedError -> {
                            uiState = HomeUiState.Error(ret.errorResult) // stop loading.
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
        const val LOG_TAG = "HomeViewModel"
    }
}