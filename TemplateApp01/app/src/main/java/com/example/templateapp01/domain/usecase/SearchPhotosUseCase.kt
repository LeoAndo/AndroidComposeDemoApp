package com.example.templateapp01.domain.usecase

import com.example.templateapp01.data.SafeResult
import com.example.templateapp01.data.repository.UnsplashRepository
import com.example.templateapp01.model.UnSplashPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SearchPhotosUseCase @Inject constructor(
    private val unsplashRepository: UnsplashRepository
) {
    suspend operator fun invoke(query: String): SafeResult<List<UnSplashPhoto>> {
        return withContext(Dispatchers.Default) {
            unsplashRepository.searchPhotos(query)
        }
    }
}