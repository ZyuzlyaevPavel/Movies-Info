package com.pvz.movies.ui.list

import androidx.lifecycle.Observer
import com.pvz.movies.model.data.Film
import com.pvz.movies.model.data.Genre
import com.pvz.movies.model.repository.FilmRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmListPresenter @Inject constructor(private val repository: FilmRepository) :
    FilmListContract.FilmListPresenter {
    var view: FilmListContract.FilmListView? = null

    private val filmDataObserver:Observer<List<Film>> = Observer{
        updateFilmRecyclerUI(it)
    }

    private val genreDataObserver:Observer<List<Genre>> = Observer{
        updateGenreRecyclerUI(it)
    }

    override fun takeView(view: FilmListContract.FilmListView) {
        this.view = view
        repository.apply {
            if (!filmsList.value.isNullOrEmpty())
                updateFilmRecyclerUI(filmsList.value)
            else
                filmsList.observeForever(filmDataObserver)
            if (!genreList.value.isNullOrEmpty())
                updateGenreRecyclerUI(genreList.value)
            else
                genreList.observeForever(genreDataObserver)
        }

    }

    private fun updateFilmRecyclerUI(
        filmList: List<Film>?
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            view?.updateFilmRecycler(filmList)
        }
    }

    private fun updateGenreRecyclerUI(
        genreList: List<Genre>?
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            view?.updateGenresRecycler(genreList)
        }
    }

    override fun dropView() {
        this.view = null
        repository.filmsList.removeObserver(filmDataObserver)
    }


}