package com.msimbiga.moviesdb.core.data.di

import com.msimbiga.moviesdb.core.data.repository.MoviesRepositoryImpl
import com.msimbiga.moviesdb.core.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository
}