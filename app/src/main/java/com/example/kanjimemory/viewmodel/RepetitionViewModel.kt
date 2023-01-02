package com.example.kanjimemory.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.repository.KanjiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepetitionViewModel @Inject constructor(private val repository: KanjiRepository): ViewModel() {

    private val _randomKanji = MutableStateFlow<Kanji?>(null)
    val randomKanji = _randomKanji.asStateFlow()

    init {
        getOneRandomKanji()
    }

    fun getOneRandomKanji() = viewModelScope.launch(Dispatchers.IO) {
        repository.getOneRandomKanji().distinctUntilChanged().collect { kanjiToCollect ->
            if(kanjiToCollect.equals(null)) {
                Log.d("databasetryout","No kanjis in your database!")
            } else {
                _randomKanji.value = kanjiToCollect
            }
        }
    }

    fun isInReviewList() {
        while (_twoMinuteReview.contains(randomKanji.value)) {
            getOneRandomKanji()
        }
    }


    val translationToCheck: MutableLiveData<String> by lazy {
        MutableLiveData<String> ("")
    }

    private var _twoMinuteReview = mutableListOf<Kanji>()

    val displayToast: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }

    fun checkTranslation() {
            if (translationToCheck.value == randomKanji.value?.translation) {
                _twoMinuteReview.add(randomKanji.value!!)
                displayToast.value = "You got it! ^_^"
                println(_twoMinuteReview)
            } else {
                displayToast.value = "Correct translation: ${randomKanji.value?.translation}"
            }
    }


}