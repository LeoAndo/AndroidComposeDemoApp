package com.example.templateapp01.di

import com.example.templateapp01.data.repository.UnsplashRepository
import com.example.templateapp01.data.repository.impl.UnsplashRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    internal abstract fun bindUnsplashRepository(impl: UnsplashRepositoryImpl): UnsplashRepository
}