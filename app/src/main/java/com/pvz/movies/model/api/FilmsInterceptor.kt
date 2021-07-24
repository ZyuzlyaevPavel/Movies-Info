package com.pvz.movies.model.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import kotlin.jvm.Throws

class FilmsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        if (response.isSuccessful) {
            val body = response.body()

            if (body != null) {
                try {
                    val newBody = responseBody(body.contentType(), body.byteStream())
                    response = response.newBuilder()
                        .body(newBody)
                        .build()
                }
                catch (ex:Exception){
                    ex.printStackTrace()
                }
            }
        }

        return response
    }

    @Throws(Exception::class)
    private fun responseBody(contentType: MediaType?, inputStream: InputStream): ResponseBody {
        val jsonResponse = Gson().fromJson(InputStreamReader(inputStream), JsonObject::class.java)
        val films = jsonResponse.getAsJsonArray("films").toString()
        return ResponseBody.create(
            contentType,
            films
        )
    }


}