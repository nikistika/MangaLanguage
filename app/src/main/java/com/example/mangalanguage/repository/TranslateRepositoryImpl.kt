package com.example.mangalanguage.repository

import com.example.mangalanguage.models.GoogleTranslate.TranslationResult
import com.example.mangalanguage.models.GoogleTranslate.Translations
import com.example.mangalanguage.network.TranslateApiService
import javax.inject.Inject

class TranslateRepositoryImpl @Inject constructor(
    private val translateApiService: TranslateApiService
): TranslateRepository {

    override suspend fun translateEnRu(textEn: String): String {
        val response = translateApiService.translateEnRu(textEn)

        if (response.isSuccessful) {
            val translateDataResult = response.body()
            if (translateDataResult != null) {
                val translations = translateDataResult.data.translations
                // Преобразование списка переводов в список строк
                val translatedTextList = translations.map { it.translatedText }
                // Преобразование списка строк в одну строку, разделенную переносами строки
                return translatedTextList.joinToString(separator = "\n")
            }
        }
        return ""
    }
}