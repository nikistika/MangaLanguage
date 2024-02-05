package com.example.mangalanguage.interfaces

/**
 * Интерфейс, позволяющий передать ссылку изображения из ReaderAdapterVP в ReaderActivity для кропа
 */

interface SendUrlImage {

    fun sendUrlImage(imageUrl: String)

}