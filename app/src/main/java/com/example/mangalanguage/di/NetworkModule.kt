package com.example.mangalanguage.di

import com.example.mangalanguage.network.MangaApiClient
import com.example.mangalanguage.network.MangaDexApiService
import com.example.mangalanguage.network.TranslateApiClient
import com.example.mangalanguage.network.TranslateApiService
import com.example.mangalanguage.repository.MangaDexRepository
import com.example.mangalanguage.repository.MangaDexRepositoryImpl
import com.example.mangalanguage.repository.TranslateRepository
import com.example.mangalanguage.repository.TranslateRepositoryImpl
import com.example.mangalanguage.view.manga_activity.TranslateActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideMangaDexRepository(mangaDexApi: MangaDexApiService): MangaDexRepository {
        return MangaDexRepositoryImpl(mangaDexApi)
    }

    @Provides
    fun provideTranslateRepository(translateApi: TranslateApiService): TranslateRepository {
        return TranslateRepositoryImpl(translateApi)
    }

    @Provides
    fun provideMangaDexApi(): MangaDexApiService {
        return MangaApiClient.getInstance().create(MangaDexApiService::class.java)
    }

    @Provides
    fun provideTranslateApi(): TranslateApiService {
        return TranslateApiClient.getInstance().create(TranslateApiService::class.java)
    }

}