package com.example.mangalanguage.network

import com.example.mangalanguage.models.GoogleTranslate.TranslationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslateApiService {
    @GET("language/translate/v2")
    suspend fun translate(
        @Query("q") query: String,
        @Query("source") sourceLanguage: String,
        @Query("target") targetLanguage: String,
        @Query("key") apiKey: String,
    ): TranslationResponse
}