package com.pvz.movies.ui.details

import androidx.lifecycle.Observer
import com.pvz.movies.model.data.Film
import com.pvz.movies.model.repository.FilmRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmDetailsPresenter @Inject constructor(private val repository: FilmRepository) :
    FilmDetailsContract.FilmDetailsPresenter {
    var view: FilmDetailsContract.FilmDetailsView? = null
    private val filmObserver: Observer<Film> = Observer {
        updateFilmDetailsUI(it)
    }

    init {
    }

    override fun takeView(view: FilmDetailsContract.FilmDetailsView) {
        this.view = view
    }

    override fun acquireFilmData(filmId:Long){
        repository.getFilmById(filmId).observeForever(filmObserver)
    }


    override fun dropView() {
        this.view = null
        repository.lastSelectedFilm.removeObserver(filmObserver)
    }

    private fun updateFilmDetailsUI(
        film: Film
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            view?.updateFilmDetails(film)
        }
    }


}