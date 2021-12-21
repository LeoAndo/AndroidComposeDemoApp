package com.example.templateapp01.ui.search

import com.example.templateapp01.data.ErrorResult
import com.example.templateapp01.model.UnSplashPhoto

internal sealed interface UiState {
    object NoPhotos : UiState
    data class Photos(val results: List<UnSplashPhoto> = emptyList()) : UiState
    object Loading : UiState
    data class OtherError(val errorMessage: String) : UiState
    data class Error(val result: ErrorResult) : UiState
}