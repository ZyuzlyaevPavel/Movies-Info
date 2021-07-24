package com.pvz.movies.ui.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.pvz.movies.model.data.Film
import com.pvz.movies.model.data.Genre
import com.pvz.movies.model.repository.FilmDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmListPresenter @Inject constructor(private val repository: FilmDataSource) :
    FilmListContract.FilmListPresenter {
    var view: FilmListContract.FilmListView? = null
    private val filmsList: MutableLiveData<List<Film>> = MutableLiveData()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val list = repository.fetchFilmList()
            Log.d("test", "$list")
            filmsList.postValue(list)
        }
    }
    val filmListObserver:Observer<List<Film>> = Observer{
            updateViewRecyclers(it)
    }
    override fun takeView(view: FilmListContract.FilmListView) {
        this.view = view
        if (!filmsList.value.isNullOrEmpty())
            updateViewRecyclers(filmsList.value)
        else
            filmsList.observeForever(filmListObserver)

    }

    private fun updateViewRecyclers(
        list: List<Film>?
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            view?.updateFilmRecycler(list)
            view?.updateGenresRecycler(listOf(Genre(0, "test")))
        }
    }

    override fun dropView() {
        this.view = null
        filmsList.removeObserver(filmListObserver)
    }


}