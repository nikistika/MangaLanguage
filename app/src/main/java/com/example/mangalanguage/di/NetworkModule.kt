package com.example.mangalanguage.di

import com.example.mangalanguage.network.MangaApiClient
import com.example.mangalanguage.network.MangaDexApiService
import com.example.mangalanguage.repository.MangaDexRepository
import com.example.mangalanguage.repository.MangaDexRepositoryImpl
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
    fun provideMangaDexApi(): MangaDexApiService {
        return MangaApiClient.getInstance().create(MangaDexApiService::class.java)
    }

}