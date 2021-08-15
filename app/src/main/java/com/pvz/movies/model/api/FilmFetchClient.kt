package com.pvz.movies.model.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object FilmFetchClient {
    private const val BASE_URL = "https://s3-eu-west-1.amazonaws.com"

    fun getClient():FilmFetchService{

         val client=OkHttpClient.Builder()
             .connectTimeout(60,TimeUnit.SECONDS)
            // .addInterceptor (ErrorHandlingInterceptor())
             .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            //.addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(FilmFetchService::class.java)

    }
}