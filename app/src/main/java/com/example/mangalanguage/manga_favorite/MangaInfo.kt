package com.example.mangalanguage.manga_favorite

import com.example.mangalanguage.R


data class MangaInfo(
    val name: String?,
    val description: String?,
    val image: String?
): ListItem() {
    override val viewType: Int = R.layout.item_info
    }
