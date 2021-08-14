package com.pvz.movies.model.api

import com.pvz.movies.model.data.Films
import retrofit2.Call
import retrofit2.http.GET

interface FilmFetchService {
    //https://s3-eu-west-1.amazonaws.com/sequeniatesttask/films.json

    @GET("/sequeniatesttask/films.json")
    fun getFilms():Call<Films>

    @GET("/sequeniatesttask/films.json")
    fun getString():Call<String>
}