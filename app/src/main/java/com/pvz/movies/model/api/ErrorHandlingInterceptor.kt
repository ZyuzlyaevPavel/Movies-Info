package com.pvz.movies.model.api

import okhttp3.Interceptor
import okhttp3.Response

class ErrorHandlingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (!response.isSuccessful) {
            when(response.code()){
                else->throw RuntimeException("Server error code: ${response.code()} with error message: ${response.message()}")
            }
        }

        return response
    }


}