package com.example.mangalanguage.repository

import com.example.mangalanguage.models.GoogleTranslate.TranslationResult

interface TranslateRepository {

    suspend fun translateEnRu (textEn: String) : TranslationResult

}