package com.pvz.movies.ui.list

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.pvz.movies.model.data.Film
import com.pvz.movies.model.data.Genre
import com.pvz.movies.model.repository.FilmRepository
import com.pvz.movies.utils.GlobalExtensions.observeOnce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmListPresenter @Inject constructor(private val repository: FilmRepository) :
    FilmListContract.FilmListPresenter {
    var view: FilmListContract.FilmListView? = null
    private val selectedGenre: MutableLiveData<Genre> = MutableLiveData()
    private val noSelection: MutableLiveData<Boolean> = MutableLiveData()

    override fun requestFilteredFilmsByGenre(genre: Genre) {
        selectedGenre.postValue(genre)
    }

    override fun requestUnfilteredFilms() {
        noSelection.postValue(true)
    }

    private val onConfigChangeSelection: MediatorLiveData<Any> =
        MediatorLiveData<Any>().also { mediator ->
            mediator.addSource(selectedGenre) {
                mediator.value = it
            }
            mediator.addSource(noSelection) {
                mediator.value = it
            }
        }

    private val liveDataMerger: MediatorLiveData<*> = MediatorLiveData<Any>().also { mediator ->
        mediator.addSource(repository.filmsList) { mediator.value = it }
        mediator.addSource(repository.genreList) { mediator.value = it }
    }


    override fun takeView(view: FilmListContract.FilmListView) {
        Log.d("test", "takeView")
        this.view = view
        view.notifyDataLoading()

        liveDataMerger.observe(view, {
            notifyDataAquisitionUI()
        })
        repository.filmsList.observe(view, { films ->
            repository.genreList.observe(view, { genreList ->
                updateGenreRecyclerUI(genreList).let {
                    selectedGenre.observe(view, { genre ->
                        filterFilmsByGenre(films, genre)
                    })
                    noSelection.observe(view, {
                        updateFilmRecyclerUI(films)
                    })

                    onConfigChangeSelection.observeOnce(view, Observer {
                        if (it is Genre)
                            selectGenreUI(view, it)
                        else
                            updateFilmRecyclerUI(films)
                    })
                }
            })
        })


    }

    private fun filterFilmsByGenre(
        values: List<Film>?,
        genre: Genre
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            if (!values.isNullOrEmpty()) {
                val filteredList = mutableListOf<Film>()
                for (i in values) {
                    if (i.genres.any { name ->
                            genre.name == name
                        })
                        filteredList.add(i)
                }
                if (filteredList.isEmpty())
                    filteredList.addAll(values)
                updateFilmRecyclerUI(filteredList)
            }
        }
    }

    private fun selectGenreUI(
        view: FilmListContract.FilmListView,
        genre: Genre
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            view.selectGenre(genre)
        }
    }

    private fun updateFilmRecyclerUI(
        filmList: List<Film>?
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            view?.updateFilmRecycler(filmList?.toMutableList())
        }
    }

    private fun notifyDataAquisitionUI() {
        CoroutineScope(Dispatchers.Main).launch {
            view?.notifyDataAquisition()
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
        Log.d("test", "dropView")
        this.view = null
    }

}