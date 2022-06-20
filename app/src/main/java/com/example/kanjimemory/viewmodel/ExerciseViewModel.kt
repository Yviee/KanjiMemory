package com.example.kanjimemory.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
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
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(private val repository: KanjiRepository): ViewModel(){

    private val _randomKanjiList = MutableStateFlow<List<Kanji>> (emptyList())
    val randomKanjiList = _randomKanjiList.asStateFlow()

    init {
        getRandomKanjis()
    }

    fun getRandomKanjis() = viewModelScope.launch(Dispatchers.IO) {
        repository.getRandomKanjis().distinctUntilChanged().collect { listOfRandomKanjis ->
            if (listOfRandomKanjis.isNullOrEmpty()) {
                Log.d("databasetryout", "No random kanjis here!")
            } else {
                _randomKanjiList.value = listOfRandomKanjis
            }
        }
    }

    val cardClicked: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    private var _solvedKanjis = MutableLiveData<List<Int>>(emptyList())
    val solvedKanjis: LiveData<List<Int>> = _solvedKanjis

    private val _solvedKanjiList = mutableListOf<Int>()
    val solvedKanjiList: List<Int>
            get() = _solvedKanjiList

    private var _selectedKanjiList = mutableListOf<Int>()
    val selectedKanjiList: List<Int>
    get() = _selectedKanjiList

    private var matchedCardsNumber = 0

    val kanjiEnabled: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(true)
    }

    val translationEnabled: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(true)
    }


    /*fun checkMatch() {
    // https://www.answertopia.com/jetpack-compose/a-jetpack-compose-viewmodel-tutorial/
    // https://getridbug.com/android/mutablelivedata-observe-method-runs-but-doesnt-update-jetpack-compose-list/
    // https://medium.com/@takahirom/jetpack-compose-state-guideline-494d467b6e76
        // checks if kanji & translation are matched correctly or not via ID. If IDs match, card should no longer be clickable
        // ExerciseScreen should observe checkMatch var, ViewModel should observe clicks in ExerciseScreen
        // better: make list of clicked buttons -> if matched: increase counter; when reload, set counter to 0
        // disable composable to make it unclickable
        // track, which kanjis have been matched and which haven't -> 2 lists for correctly selected kanjis
        // write function: isSelected -> see whether kanji/translation was selected; return true/false

        println("Translation Id before calculation is: ${translationIdClicked.value}")
        println("Kanji Id before calculation is: ${kanjiIdClicked.value}")

        if (matchedCardsNumber < 5) {
            if (kanjiClicked.value == true && translationClicked.value == true) {
                if (kanjiIdClicked.value == translationIdClicked.value) {
                    matchedCardsNumber++
                    //matchedKanjiList.value?.let { _listOfMatches.add(it) }
                    println(matchOfCards.value)
                    //_matchOfCards.value = false
                    println(matchedCardsNumber)
                    println(matchOfCards.value)
                    resetVariables()
                    // add both kanji and translation to each list respectively
                } else if (kanjiIdClicked.value != translationIdClicked.value || translationClicked.value != kanjiClicked.value) {
                    resetVariables()
                    println(matchedCardsNumber)
                }
            }
        }
    }*/
/* fun resetVariables() {
        kanjiIdClicked.value = null
        translationIdClicked.value = null
        kanjiClicked.value = false
        translationClicked.value = false
    }*/

    fun reload() {
        // checks if all kanjis & translations were matched and none are clickable anymore so as to instigate a reload of all cards
        if (matchedCardsNumber == 5) {
            getRandomKanjis()
            matchedCardsNumber = 0
            _solvedKanjiList.clear()
            _solvedKanjis.value = listOf()
        }
    }

    fun addToSelected(kanjiId: Int) {
        // check if not empty
        // if ITEM has same id: matchedcardsnumber++, add id to solved Kanjis, clear selectedKanjis
        // if id in solved list, disable


        if (_selectedKanjiList.isNotEmpty()) {
            // if guessed correctly:
            if (_selectedKanjiList.contains(kanjiId)) {
                matchedCardsNumber++
                _solvedKanjiList.add(kanjiId)
                val copyList = _solvedKanjiList.toList()
                // need to make a deep copy of list: if only list is referenced, it will not trigger recomposition
                _solvedKanjis.value = copyList
                cardClicked.value = false
                kanjiEnabled.value = false
                translationEnabled.value = false
            } else {
                cardClicked.value = false
            }
            _selectedKanjiList.clear()
            cardClicked.value = false

        } else {
            _selectedKanjiList.add(kanjiId)
        }
    }

    fun disableCard(kanjiId: Int): Boolean {
        // check if id of kanji is in solved list
        // if in solved list -> return false
        // otherwise true

        if(_solvedKanjiList.contains(kanjiId)) {
            cardClicked.value = false
            _selectedKanjiList.clear()
            return false
        }
        return  true
    }

}


// ToDo: create mutable variables that hold state and change when disableCard is called
// create kanjiSelectList & translationSelectList
// if (kanjiSelectList.value == translationSelectList.value) -> disableCard where id == kanjiSelectList.value && translationSelectList.value
// create counter: if kanjiClicked -> counter ++; if kanji == translation -> counter++
// if counter = 2 -> disableCard function should be called
// -> each card has their own counter
// none of these will ever work, because they are all just looking at one variable inside viewmodel
// -> if viewmodel variable changes, then all observing cards will change together
