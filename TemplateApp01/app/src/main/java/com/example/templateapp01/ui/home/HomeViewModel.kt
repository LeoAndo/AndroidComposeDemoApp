package com.example.templateapp01.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.templateapp01.data.ErrorResult
import com.example.templateapp01.data.SafeResult
import com.example.templateapp01.domain.usecase.SearchPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val searchPhotosUseCase: SearchPhotosUseCase
) : ViewModel() {

    var uiState = mutableStateOf<UiState>(UiState.NoPhotos)
        private set

    init {
        Log.d(LOG_TAG, "init: " + hashCode())
        uiState.value = UiState.Loading // start loading.

        viewModelScope.launch {
            when (val ret = searchPhotosUseCase("dogs")) {
                is SafeResult.Success -> {
                    uiState.value = UiState.Photos(results = ret.data) // stop loading.
                }
                is SafeResult.Error -> {
                    when (ret.errorResult) {
                        is ErrorResult.BadRequestError, is ErrorResult.NetworkError,
                        is ErrorResult.NotFoundError, is ErrorResult.OtherError, is ErrorResult.UnAuthorizedError -> {
                            uiState.value = UiState.Error(ret.errorResult) // stop loading.
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