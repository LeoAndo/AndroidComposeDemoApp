package com.example.templateapp01.domain.usecase

import com.example.templateapp01.domain.repository.UnsplashRepository
import com.example.templateapp01.di.DefaultDispatcher
import com.example.templateapp01.domain.model.UnSplashPhoto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SearchPhotosUseCase @Inject constructor(
    private val unsplashRepository: UnsplashRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(query: String): Result<List<UnSplashPhoto>> {
        return withContext(dispatcher) {
            unsplashRepository.searchPhotos(query)
        }
    }
}