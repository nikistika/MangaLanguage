package com.example.mangalanguage.models.GoogleTranslate

data class TranslationRequest(
    val query: String,
    val sourceLanguage: String,
    val targetLanguage: String,
    val apiKey: String
)