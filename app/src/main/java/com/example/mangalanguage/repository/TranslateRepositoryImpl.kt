package com.example.mangalanguage.repository

import com.example.mangalanguage.models.GoogleTranslate.TranslationResult
import com.example.mangalanguage.models.GoogleTranslate.Translations
import com.example.mangalanguage.network.TranslateApiService
import javax.inject.Inject

class TranslateRepositoryImpl @Inject constructor(
    private val translateApiService: TranslateApiService
): TranslateRepository {

    override suspend fun translateEnRu(textEn: String): TranslationResult {
        val response = translateApiService.translateEnRu(textEn)

        if (response.isSuccessful){

            val translateDataResult = response.body()
            if (translateDataResult != null){
                return TranslationResult(
                    data = Translations(translations = translateDataResult.data.translations)
                )
            }
        }
        return TranslationResult(
            data = Translations(translations = emptyList())
        )
    }
}