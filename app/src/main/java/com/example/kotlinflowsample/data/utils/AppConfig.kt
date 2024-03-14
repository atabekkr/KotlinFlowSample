package com.example.kotlinflowsample.data.utils

import com.example.kotlinflowsample.data.network.ApiService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppConfig {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    fun ApiService(): ApiService =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ApiService::class.java)
}