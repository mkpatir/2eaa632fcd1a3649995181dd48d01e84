package com.mkpatir.spacedelivery.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppServiceFactory {

    fun buildService(): AppService{
        val retrofit = Retrofit.Builder()
            .baseUrl("")
            .client(buildHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(AppService::class.java)
        return service
    }

    private fun buildHttpClient(): OkHttpClient{
        val okHttpClientBuilder = OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return okHttpClientBuilder.build()
    }
}