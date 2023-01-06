package com.example.kanjimemory.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.repository.KanjiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue

@HiltViewModel
class RepetitionViewModel @Inject constructor(private val repository: KanjiRepository): ViewModel() {

    private val _randomKanji = MutableStateFlow<Kanji?>(null)
    val randomKanji = _randomKanji.asStateFlow()

    private val numberOfRepetitions = ArrayList<Int>()

    init {
        numberOfRepetitions.add(1)
        numberOfRepetitions.add(2)
        numberOfRepetitions.add(3)
        getOneRandomKanji()
    }

    fun getOneRandomKanji(): Job = viewModelScope.launch(Dispatchers.IO) {

        repository.getOneRandomKanji().distinctUntilChanged().collect { kanjiToCollect ->
            if(kanjiToCollect.equals(null)) {
                Log.d("databasetryout","No kanjis in your database!")
            } else {
                if(kanjiToCollect.counter > 0){

                    when(kanjiToCollect.counter) {
                        1 -> if ((Date().time - kanjiToCollect.dateTranslated).absoluteValue >= 2*60000) {
                            _randomKanji.value = kanjiToCollect
                        } else{
                            getOneRandomKanji()
                        }
                        2 -> if ((Date().time - kanjiToCollect.dateTranslated).absoluteValue >= 4*60000) {
                            _randomKanji.value = kanjiToCollect
                        } else{
                            getOneRandomKanji()
                        }
                        3 -> if ((Date().time - kanjiToCollect.dateTranslated).absoluteValue >= 6*60000) {
                            _randomKanji.value = kanjiToCollect
                        } else{
                            getOneRandomKanji()
                        }
                        else -> getOneRandomKanji()
                    }

                    //val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a")
                    //val dateFormat = SimpleDateFormat("hh:mm", Locale.getDefault()
                    // calender object initialised with current date & time
                    /*val calendar = Calendar.getInstance()
                    calendar.time = Date(kanjiToCollect.dateTranslated)

                    // add number of repetitions saved in spaced reps to calendar (minutes)?
                    calendar.add(Calendar.MINUTE, numberOfRepetitions[kanjiToCollect.counter - 1])
                    // numberOfRepetitions = 1 --> after 2 minutes; 2 = after 4 minutes; 3 = after 6 minutes

                    if(calendar.time.time < Date().time){
                        _randomKanji.value = kanjiToCollect
                    }*/
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

    private fun updateKanji(kanji: Kanji) = viewModelScope.launch { repository.update(kanji) }

    val translationToCheck: MutableLiveData<String> by lazy {
        MutableLiveData<String> ("")
    }

    val displayToast: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }

    fun checkTranslation() {

        randomKanji.value?.dateTranslated = Date().time

        if (translationToCheck.value == randomKanji.value?.translation) {
            val kanjiToUpdate: Kanji = randomKanji.value!!
            kanjiToUpdate.counter++
            displayToast.value = "You got it! ^_^"
            updateKanji(kanjiToUpdate)
        } else {
            if (randomKanji.value?.counter!! > 0) {
                val kanjiToUpdate: Kanji = randomKanji.value!!
                kanjiToUpdate.counter--
                updateKanji(kanjiToUpdate)
            }
            displayToast.value = "Correct translation: ${randomKanji.value?.translation}"
        }
    }
}