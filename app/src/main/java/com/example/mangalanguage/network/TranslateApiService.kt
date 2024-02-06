package com.example.mangalanguage.network

import com.example.mangalanguage.models.GoogleTranslate.TranslationResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslateApiService {
    @GET("language/translate/v2")
    suspend fun translateEnRu(
        @Query("q") query: String,
        @Query("source") sourceLanguage: String = "en",
        @Query("target") targetLanguage: String = "ru",
        @Query("key") apiKey: String = "AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw"
    ): Response<TranslationResult>
}