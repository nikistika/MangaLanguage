package com.example.mangalanguage.viewModel

import android.annotation.SuppressLint
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mangalanguage.repository.TranslateRepository
import com.example.mangalanguage.view.manga_activity.TranslateActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TranslateViewModel @Inject constructor(
    private val translateRepository: TranslateRepository
) : ViewModel() {

    @SuppressLint("StaticFieldLeak")

    private val translate: MutableLiveData<String> = MutableLiveData()
    val translateResult: LiveData<String> = translate

    suspend fun translateEnRu(textEn: String) {
        val translateString = translateRepository.translateEnRu(textEn)
        translate.postValue(translateString)
    }

}