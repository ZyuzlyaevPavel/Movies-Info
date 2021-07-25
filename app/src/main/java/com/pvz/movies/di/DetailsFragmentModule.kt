package com.pvz.movies.di

import com.pvz.movies.model.repository.FilmRepository
import com.pvz.movies.ui.info.FilmDetailsContract
import com.pvz.movies.ui.info.FilmDetailsPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DetailsFragmentModule {

    @Provides
    @Singleton
    fun providePresenter(repository: FilmRepository): FilmDetailsContract.FilmDetailsPresenter {
        return FilmDetailsPresenter(repository)
    }
}