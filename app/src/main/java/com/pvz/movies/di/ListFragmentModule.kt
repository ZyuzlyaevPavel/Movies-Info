package com.pvz.movies.di

import com.pvz.movies.model.repository.FilmDataSource
import com.pvz.movies.ui.list.FilmListContract
import com.pvz.movies.ui.list.FilmListPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ListFragmentModule {

    @Provides
    @Singleton
    fun providePresenter(dataSource: FilmDataSource): FilmListContract.FilmListPresenter {
        return FilmListPresenter(dataSource)
    }
}