package com.example.mangalanguage.repository

import com.example.mangalanguage.models.MangaDex.MangaChapters.MangaChapter
import com.example.mangalanguage.models.MangaDex.MangaData.MangaData
import com.example.mangalanguage.models.MangaDex.MangaImage.Chapter
import com.example.mangalanguage.models.MangaDex.MangaImage.MangaImage
import com.example.mangalanguage.network.MangaDexApiService
import javax.inject.Inject

class MangaDexRepositoryImpl @Inject constructor(
    private val mangadexApi: MangaDexApiService
): MangaDexRepository {


    override suspend fun getMangaList(title: String): MangaData {
        val response = mangadexApi.getMangaData(title)
        if (response.isSuccessful){
            val mangaDataResult = response.body()
            if (mangaDataResult != null){
                return MangaData(
                    data = mangaDataResult.data,
                    limit = mangaDataResult.limit,
                    offset = mangaDataResult.offset,
                    response = mangaDataResult.response,
                    result = mangaDataResult.result,
                    total = mangaDataResult.total
                )
            }
        }
        return MangaData(
            `data` = emptyList(),
            limit = 0,
            offset = 0,
            response = "",
            result = "",
            total = 0
        )
    }

    override suspend fun getMangaChapters(mangaId: String?, offset: Int): MangaChapter {
        val response = mangadexApi.getMangaChapter(mangaId, offset)
        if (response.isSuccessful){
            val mangaChapterResult = response.body()
            if (mangaChapterResult != null){
                return MangaChapter(
                    data = mangaChapterResult.data,
                    limit = mangaChapterResult.limit,
                    offset = mangaChapterResult.offset,
                    response = mangaChapterResult.response,
                    result = mangaChapterResult.result,
                    total = mangaChapterResult.total
                )
            }
        }
        return MangaChapter(
            data = emptyList(),
            limit = 0,
            offset = 0,
            response = "",
            result = "",
            total = 0
        )
    }

    override suspend fun getMangaImages (idChapter: String?): MangaImage {
        val response = mangadexApi.getMangaImage(idChapter)
        if (response.isSuccessful){
            val mangaImageResult = response.body()
            if (mangaImageResult != null){
                return MangaImage(
                    baseUrl = mangaImageResult.baseUrl,
                    chapter = mangaImageResult.chapter,
                    result = mangaImageResult.result
                )
            }
        }
        return MangaImage(
            baseUrl = "",
            chapter = Chapter(emptyList(),emptyList(),""),
            result = ""
        )
    }
}