package com.example.mangalanguage.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mangalanguage.models.MangaDex.MangaChapters.MangaChapter
import com.example.mangalanguage.repository.MangaDexRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MangaInfoViewModel @Inject constructor(
    private val mangaDexRepository: MangaDexRepository
): ViewModel() {

    private val mangaChapter: MutableLiveData<MangaChapter> = MutableLiveData()
    val mangaChapterResult: LiveData<MangaChapter> = mangaChapter

    suspend fun getMangaChapters(mangaId: String?, offset: Int) {
        val mangaChapters = mangaDexRepository.getMangaChapters(mangaId, offset)
        mangaChapter.postValue(mangaChapters)
    }

}