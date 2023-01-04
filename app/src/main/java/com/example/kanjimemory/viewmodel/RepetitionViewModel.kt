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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class RepetitionViewModel @Inject constructor(private val repository: KanjiRepository): ViewModel() {

    private val _randomKanji = MutableStateFlow<Kanji?>(null)
    val randomKanji = _randomKanji.asStateFlow()

    private val spacedRepetitions = ArrayList<Int>()

    init {
        spacedRepetitions.add(1)
        spacedRepetitions.add(2)
        getOneRandomKanji()
    }

    fun getOneRandomKanji() = viewModelScope.launch(Dispatchers.IO) {
        repository.getOneRandomKanji().distinctUntilChanged().collect { kanjiToCollect ->
            if(kanjiToCollect.equals(null)) {
                Log.d("databasetryout","No kanjis in your database!")
            } else {
                if(kanjiToCollect.counter > 0){
                    //val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a")
                    //val dateFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
                    val calendar = Calendar.getInstance()
                    calendar.time = Date(kanjiToCollect.dateTranslated)
                    calendar.add(Calendar.MINUTE, spacedRepetitions[kanjiToCollect.counter - 1])
                    if(calendar.time.time < Date().time){
                        _randomKanji.value = kanjiToCollect
                    }
                    /*else{
                        getOneRandomKanji()
                    }*/
                }
                else{
                    _randomKanji.value = kanjiToCollect
                }
            }
        }
    }

    /*fun isInReviewList() {
        while (twoMinuteReview.contains(randomKanji.value)) {
            getOneRandomKanji()
        }
    }*/

    val translationToCheck: MutableLiveData<String> by lazy {
        MutableLiveData<String> ("")
    }

    var twoMinuteReview = mutableListOf<Kanji>()

    val displayToast: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }

    fun checkTranslation() {
        if (translationToCheck.value == randomKanji.value?.translation) {
            randomKanji.value?.counter?.plus(1)
            //twoMinuteReview.add(randomKanji.value!!)
            displayToast.value = "You got it! ^_^"
            println(twoMinuteReview)
        } else {
            displayToast.value = "Correct translation: ${randomKanji.value?.translation}"
        }
    }
}