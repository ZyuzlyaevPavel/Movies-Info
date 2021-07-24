package com.pvz.movies.model.api

import com.pvz.movies.model.data.Film
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmFetchService {
    //https://s3-eu-west-1.amazonaws.com/sequeniatesttask/films.json

    @GET("/sequeniatesttask/films.json")
    fun getFilms():Call<List<Film>>

    @GET("/sequeniatesttask/films.json")
    fun getString():Call<String>
}