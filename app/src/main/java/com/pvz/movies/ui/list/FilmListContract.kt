package com.pvz.movies.ui.list

import com.pvz.movies.model.data.Film
import com.pvz.movies.model.data.Genre
import com.pvz.movies.ui.IContract

interface FilmListContract:IContract {
    interface FilmListPresenter: IContract.IPresenter<FilmListView> {
        //fun requestFilteredFilmsByGenres(genre: List<Genre>)
        fun requestFilteredFilmsByGenre(genre: Genre)
        fun requestUnfilteredFilms()
        fun requestData()
    }
    interface FilmListView:IContract.IView<FilmListPresenter>{
        fun updateFilmRecycler(films: MutableList<Film>?)
        fun updateGenresRecycler(listOf: List<Genre>?)
        fun selectGenre(genre:Genre)
        fun notifyDataLoading()
        fun notifyDataAquisition()
        fun notifyDataLoadingFail()
    }
}