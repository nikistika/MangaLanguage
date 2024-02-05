package com.example.mangalanguage.models.MangaDex

import com.example.mangalanguage.R
import com.example.mangalanguage.models.MangaDex.ListItem


data class MangaInfo(
    val name: String?,
    val description: String?,
    val image: String?
): ListItem() {
    override val viewType: Int = R.layout.item_info
    }
