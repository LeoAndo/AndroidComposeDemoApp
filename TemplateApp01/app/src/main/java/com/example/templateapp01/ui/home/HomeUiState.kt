package com.example.templateapp01.ui.home

import com.example.templateapp01.domain.model.UnSplashPhoto

internal sealed interface HomeUiState {
    object Initial : HomeUiState
    object NoPhotos : HomeUiState
    data class Photos(val results: List<UnSplashPhoto> = emptyList()) : HomeUiState
    object Loading : HomeUiState
    data class Failure(val error: Throwable) : HomeUiState
}