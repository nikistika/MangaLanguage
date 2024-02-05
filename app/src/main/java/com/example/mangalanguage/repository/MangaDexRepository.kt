package com.example.mangalanguage.repository

import com.example.mangalanguage.models.MangaDex.MangaChapters.MangaChapter
import com.example.mangalanguage.models.MangaDex.MangaData.MangaData
import com.example.mangalanguage.models.MangaDex.MangaImage.MangaImage

interface MangaDexRepository {

    suspend fun getMangaList(title: String): MangaData

    suspend fun getMangaChapters(mangaId: String?, offset: Int): MangaChapter

    suspend fun getMangaImages (idChapter: String?): MangaImage

}