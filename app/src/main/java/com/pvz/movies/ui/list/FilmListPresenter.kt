package com.pvz.movies.ui.list

import android.util.Log
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

    private val filmDataObserver: Observer<List<Film>> = Observer {
        updateFilmRecyclerUI(it)
    }

    private val genreDataObserver: Observer<List<Genre>> = Observer {
        updateGenreRecyclerUI(it)
    }

    override fun filterFilmsByGenre(selectedGenres: List<Genre>) {
        val values = repository.filmsList.value
        CoroutineScope(Dispatchers.Default).launch {
            if (!values.isNullOrEmpty()) {
                val filteredList = mutableListOf<Film>()
                for (i in values) {
                    if (i.genres.any { name ->
                            selectedGenres.any {
                                it.name == name
                            }
                        })
                        filteredList.add(i)
                }
                if(filteredList.isEmpty())
                    filteredList.addAll(values)
                updateFilmRecyclerUI(filteredList)
            }
        }
    }

    override fun filterFilmsByGenre(genre: Genre) {
        val values = repository.filmsList.value
        CoroutineScope(Dispatchers.Default).launch {
            if (!values.isNullOrEmpty()) {
                val filteredList = mutableListOf<Film>()
                for (i in values) {
                    if (i.genres.any { name ->
                            genre.name==name
                        })
                        filteredList.add(i)
                }
                if(filteredList.isEmpty())
                    filteredList.addAll(values)
                updateFilmRecyclerUI(filteredList)
            }
        }
    }

    override fun takeView(view: FilmListContract.FilmListView) {
        Log.d("test","takeView")
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
            view?.updateFilmRecycler(filmList?.toMutableList())
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
        Log.d("test","dropView")
        this.view = null
        repository.filmsList.removeObserver(filmDataObserver)
    }


}