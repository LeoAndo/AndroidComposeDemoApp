package com.example.templateapp01.ui.home

import com.example.templateapp01.data.ErrorResult
import com.example.templateapp01.model.UnSplashPhoto

internal sealed interface HomeUiState {
    object Initial : HomeUiState
    object NoPhotos : HomeUiState
    data class Photos(val results: List<UnSplashPhoto> = emptyList()) : HomeUiState
    object Loading : HomeUiState
    data class Error(val error: ErrorResult) : HomeUiState
}