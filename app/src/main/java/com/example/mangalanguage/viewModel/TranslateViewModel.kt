package com.example.mangalanguage.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mangalanguage.repository.TranslateRepository

class TranslateViewModel(
    private val translateRepository: TranslateRepository
) : ViewModel() {

    private val translate: MutableLiveData<String> = MutableLiveData()
    val translateResult: LiveData<String> = translate

    suspend fun translateEnRu(textEn: String) {
        val translateString = translateRepository.translateEnRu(textEn).toString()
        translate.postValue(translateString)
    }

}