package com.pvz.movies.model.repository

import androidx.lifecycle.MutableLiveData
import com.pvz.movies.model.api.FilmFetchService
import com.pvz.movies.model.data.Film
import retrofit2.awaitResponse
import java.io.IOException

class FilmDataSource(private val api: FilmFetchService) {
    val filmFetchResponse: MutableLiveData<Film> = MutableLiveData<Film>()

    suspend fun fetchFilmList(): List<Film>? {
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
}