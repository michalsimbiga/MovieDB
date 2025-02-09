package com.msimbiga.moviesdb.core.data.di

import android.content.Context
import com.msimbiga.moviesdb.core.data.datasource.MoviesLocalDataSource
import com.msimbiga.moviesdb.core.data.repository.MoviesRepositoryImpl
import com.msimbiga.moviesdb.core.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository

    companion object {

        @Provides
        @Singleton
        fun provideMovieLocalDataSource(@ApplicationContext context: Context): MoviesLocalDataSource =
            MoviesLocalDataSource(context)
    }
}