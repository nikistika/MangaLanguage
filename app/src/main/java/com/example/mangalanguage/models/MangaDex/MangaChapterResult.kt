package com.example.mangalanguage.models.MangaDex

import com.example.mangalanguage.R


data class MangaChapterResult(
    val id: String,
    val chapter: String?,
    val title: String?
    ) : ListItem() {
    override val viewType: Int = R.layout.item_chapter

    // Инициализация с использованием ViewBinding
//    fun bindViewHolder(holder: ItemChapterBinding) {
//        // Используйте holder.binding для доступа к элементам макета item_type1.xml и установки значений
//    }
}