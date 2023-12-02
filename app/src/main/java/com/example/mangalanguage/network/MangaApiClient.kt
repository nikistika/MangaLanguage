package com.example.mangalanguage.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MangaApiClient {

    private const val BASE_URL = "https://api.mangadex.org"

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}