package com.example.kanjimemory.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.repository.KanjiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue

@HiltViewModel
class RepetitionViewModel @Inject constructor(private val repository: KanjiRepository): ViewModel() {

    private val _randomKanji = MutableStateFlow<Kanji?>(null)
    val randomKanji = _randomKanji.asStateFlow()

    private val timeInterval = ArrayList<Int>()

    init {
        timeInterval.add(2)
        timeInterval.add(4)
        timeInterval.add(6)
        getOneRandomKanji()
    }

    private var flowValue: Kanji? = null

    fun getOneRandomKanji(): Job = viewModelScope.launch(Dispatchers.IO) {

        runBlocking(Dispatchers.IO) {
            flowValue = repository.getOneRandomKanji().first()

            if (flowValue!!.counter > 0) {
                val timeToNextReview = (Date().time - flowValue!!.dateTranslated).absoluteValue
                if (timeToNextReview >= timeInterval[_randomKanji.value!!.counter - 1] * 60000) {
                    _randomKanji.value = flowValue
                } else {
                    getOneRandomKanji()
                }
            } else {
                _randomKanji.value = flowValue
            }
        }
    }

    private fun updateKanji(kanji: Kanji) = viewModelScope.launch { repository.update(kanji) }

    var translationToCheck = ""

    val displayToast: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }

    val vibrationLong: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>(100)
    }

    fun checkTranslation() {

        randomKanji.value?.dateTranslated = Date().time

        if (translationToCheck == randomKanji.value?.translation) {
            val kanjiToUpdate: Kanji = randomKanji.value!!
            kanjiToUpdate.counter++
            vibrationLong.value = 100
            displayToast.value = "You got it! ^_^"
            updateKanji(kanjiToUpdate)
        } else {
            if (randomKanji.value?.counter!! > 0) {
                val kanjiToUpdate: Kanji = randomKanji.value!!
                kanjiToUpdate.counter--
                updateKanji(kanjiToUpdate)
            }
            vibrationLong.value = 500
            displayToast.value = "Correct translation: ${randomKanji.value?.translation}"
        }
    }
}