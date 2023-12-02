package com.example.mangalanguage.models.MangaDex

import com.example.mangalanguage.models.MangaDex.MangaData.Description
import com.example.mangalanguage.models.MangaDex.MangaData.Title


data class MangaDataResult(
    val title: Title,
    val year: String,
    val description: Description,
    val id: String,
    val fileName: String?
    )
