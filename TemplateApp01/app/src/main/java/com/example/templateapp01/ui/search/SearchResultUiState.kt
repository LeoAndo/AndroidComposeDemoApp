package com.example.templateapp01.ui.search

import com.example.templateapp01.data.ErrorResult
import com.example.templateapp01.model.UnSplashPhoto

internal sealed interface SearchResultUiState {
    object Initial : SearchResultUiState
    object NoPhotos : SearchResultUiState
    data class Photos(val results: List<UnSplashPhoto> = emptyList()) : SearchResultUiState
    object Loading : SearchResultUiState
    data class Error(val result: ErrorResult) : SearchResultUiState
}