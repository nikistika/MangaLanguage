package com.example.mangalanguage.network

import com.example.mangalanguage.models.MangaDex.MangaChapters.MangaChapter
import com.example.mangalanguage.models.MangaDex.MangaData.MangaData

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MangaDexApiService {

    @GET("manga")
    suspend fun getMangaData(
        @Query("title") title: String,
        @Query("includes[]") includes: List<String> = listOf("author", "cover_art")
    ): Response<MangaData>

    @GET("chapter")
    suspend fun getMangaChapter(
        @Query("manga") manga: String?,
        @Query("offset") offset: Int,
        @Query("translatedLanguage[]") translatedLanguage: String = "en",
        @Query("order[chapter]") order: String = "asc",
        @Query("limit") limit: Int = 100,
    ): Response<MangaChapter>

}


