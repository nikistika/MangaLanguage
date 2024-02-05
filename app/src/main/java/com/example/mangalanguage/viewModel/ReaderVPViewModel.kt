package com.example.mangalanguage.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mangalanguage.interfaces.GoToReader
import com.example.mangalanguage.interfaces.SendUrlImage
import com.example.mangalanguage.models.MangaDex.MangaImage.MangaImage
import com.example.mangalanguage.repository.MangaDexRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReaderVPViewModel @Inject constructor(
    private val mangaDexRepository: MangaDexRepository,
    private val sendUrlImage: SendUrlImage
): ViewModel() {

    private val mangaImage: MutableLiveData<MangaImage> = MutableLiveData()
    val mangaImageResult: LiveData<MangaImage> = mangaImage


    suspend fun getMangaImages (idChapter: String?) {
        val mangaImages = mangaDexRepository.getMangaImages(idChapter)
        mangaImage.postValue(mangaImages)
    }
}