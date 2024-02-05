package com.example.mangalanguage.interfaces

/**
 * Интерфейс, позволяющий передать id главы манги из MangaInfoActivity в ReaderActivity
 */

interface GoToReader {

    fun goToReader(id: String)

}