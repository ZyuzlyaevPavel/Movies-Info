package com.pvz.movies.ui.list

import com.pvz.movies.model.data.Film
import com.pvz.movies.model.data.Genre
import com.pvz.movies.ui.IContract

interface FilmListContract:IContract {
    interface FilmListPresenter: IContract.IPresenter<FilmListView> {

    }
    interface FilmListView:IContract.IView<FilmListPresenter>{
        fun updateFilmRecycler(films: List<Film>?)
        fun updateGenresRecycler(listOf: List<Genre>)

    }
}