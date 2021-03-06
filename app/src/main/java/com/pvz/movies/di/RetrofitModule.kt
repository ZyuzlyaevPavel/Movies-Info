package com.pvz.movies.di

import com.pvz.movies.model.api.FilmFetchClient
import com.pvz.movies.model.repository.FilmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun provideFilmDataSource(): FilmRepository {
        return FilmRepository(FilmFetchClient.getClient())
    }
}