package com.example.mangalanguage.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TranslateApiClient {
    private const val BASE_URL = "https://translation.googleapis.com/"

    fun create(): TranslateApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(TranslateApiService::class.java)
    }
}