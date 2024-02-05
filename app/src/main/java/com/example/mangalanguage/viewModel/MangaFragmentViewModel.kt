package com.example.mangalanguage.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mangalanguage.models.MangaDex.MangaChapters.MangaChapter
import com.example.mangalanguage.models.MangaDex.MangaData.MangaData
import com.example.mangalanguage.models.MangaDex.MangaImage.MangaImage
import com.example.mangalanguage.repository.MangaDexRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MangaFragmentViewModel @Inject constructor(
    private val mangaDexRepository: MangaDexRepository
): ViewModel() {

    private val mangaInfo: MutableLiveData<MangaData> = MutableLiveData()
    val mangaInfoResult: LiveData<MangaData> = mangaInfo

    suspend fun getMangaList (title: String) {
        withContext(Dispatchers.IO) {
            val mangaList = mangaDexRepository.getMangaList(title)
            mangaInfo.postValue(mangaList)
        }
    }
}