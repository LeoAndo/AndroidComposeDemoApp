package com.example.templateapp01.ui.home

import com.example.templateapp01.model.UnSplashPhoto

//data class UiState(
//    val isLoading: Boolean = false,
//    val results: List<UnSplashPhoto> = emptyList(),
//    val errorMessage: String? = null
//)
internal sealed interface UiState {
    object NoPhotos : UiState
    data class Photos(val results: List<UnSplashPhoto> = emptyList()) : UiState
    object Loading : UiState
    object Error : UiState
}