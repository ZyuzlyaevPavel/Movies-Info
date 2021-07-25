package com.pvz.movies.ui.details

import com.pvz.movies.model.data.Film
import com.pvz.movies.ui.IContract

interface FilmDetailsContract:IContract {
    interface FilmDetailsPresenter: IContract.IPresenter<FilmDetailsView> {

        fun acquireFilmData(filmId: Long)
    }
    interface FilmDetailsView:IContract.IView<FilmDetailsPresenter>{
        fun updateFilmDetails(film: Film)

    }
}