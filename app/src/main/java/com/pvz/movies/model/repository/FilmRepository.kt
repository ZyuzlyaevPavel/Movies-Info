package com.pvz.movies.model.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.pvz.movies.model.api.FilmFetchService
import com.pvz.movies.model.data.Film
import com.pvz.movies.model.data.Genre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import java.io.IOException

class FilmRepository(private val api: FilmFetchService) {
    val filmsList = MutableLiveData<List<Film>>()
    val genreList = MutableLiveData<List<Genre>>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val localFilmList = fetchFilmList()
            val localGenreList = mutableListOf<Genre>()
            Log.d("test", "localFilmList -> $localFilmList")
            filmsList.postValue(localFilmList)

            if (localFilmList != null)
                parseGenres(localFilmList, localGenreList)
            Log.d("test", "localGenreList -> $localGenreList")
            genreList.postValue(localGenreList)
        }
    }

    private fun parseGenres(
        list: List<Film>,
        localGenreList: MutableList<Genre>
    ) {
        for (i in list.iterator()) {
            for (j in i.genres) {
                val element = Genre(j)
                if (!localGenreList.contains(element))
                    localGenreList.add(element)
            }
        }
    }


    private suspend fun fetchFilmList(): List<Film>? {
        try {
            val response = api.getFilms().awaitResponse()
            if (response.isSuccessful) {
                return response.body()
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return null
    }

    val lastSelectedFilm = MutableLiveData<Film>()

    fun getFilmById(filmId: Long): MutableLiveData<Film> {
        val films = filmsList.value
        if (!films.isNullOrEmpty())
            for (i in films.iterator()) {
                if (i.id == filmId)
                    lastSelectedFilm.postValue(i)
            }
        return lastSelectedFilm
    }


}