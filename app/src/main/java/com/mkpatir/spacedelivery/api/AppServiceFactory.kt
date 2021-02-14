package com.mkpatir.spacedelivery.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppServiceFactory {

    private const val BASE_URL = "https://run.mocky.io/v3/"

    fun buildService(): AppService{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(buildHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AppService::class.java)
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