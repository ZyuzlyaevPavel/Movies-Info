package com.pvz.movies.di

import com.pvz.movies.model.repository.FilmRepository
import com.pvz.movies.ui.details.FilmDetailsContract
import com.pvz.movies.ui.details.FilmDetailsPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class DetailsFragmentModule {

    @Provides
    @FragmentScoped
    fun providePresenter(repository: FilmRepository): FilmDetailsContract.FilmDetailsPresenter {
        return FilmDetailsPresenter(repository)
    }
}