package com.example.mangalanguage.models.MangaDex.MangaData

data class MangaData(
    val `data`: List<Data>,
    val limit: Int,
    val offset: Int,
    val response: String,
    val result: String,
    val total: Int
)