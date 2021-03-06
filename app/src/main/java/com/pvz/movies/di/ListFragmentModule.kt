package com.pvz.movies.di

import com.pvz.movies.model.repository.FilmRepository
import com.pvz.movies.ui.list.FilmListContract
import com.pvz.movies.ui.list.FilmListPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class ListFragmentModule {

    @Provides
    @FragmentScoped
    fun providePresenter(repository: FilmRepository): FilmListContract.FilmListPresenter {
        return FilmListPresenter(repository)
    }
}