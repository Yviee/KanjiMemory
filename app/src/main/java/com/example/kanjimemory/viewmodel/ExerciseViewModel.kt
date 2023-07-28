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
class ExerciseViewModel @Inject constructor(private val repository: KanjiRepository) : ViewModel() {

    private val _randomKanjiList = MutableStateFlow<List<Kanji>>(emptyList())
    val randomKanjiList = _randomKanjiList.asStateFlow()

    init {
        getRandomKanjis()
    }

    fun getRandomKanjis() = viewModelScope.launch(Dispatchers.IO) {
        repository.getRandomKanjis().distinctUntilChanged().collect { listOfRandomKanjis ->
            if (listOfRandomKanjis.isEmpty()) {
                Log.d("databasetryout", "No random kanjis here!")
            } else {
                _randomKanjiList.value = listOfRandomKanjis
            }
        }
    }

    val kanjiCardClicked: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val translationCardClicked: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    private val _solvedKanjiList = mutableListOf<Int>()
    private var _solvedKanjis = MutableLiveData<List<Int>>(emptyList())
    val solvedKanjis: LiveData<List<Int>> = _solvedKanjis

    private var _selectedKanjiList = mutableListOf<Int>()

    private var matchedCardsCounter = 0

    fun reload() {
        if (matchedCardsCounter == 5) {
            getRandomKanjis()
            matchedCardsCounter = 0
            _solvedKanjiList.clear()
            _solvedKanjis.value = listOf()
        }
    }

    fun addToSelected(kanjiId: Int) {

        if (_selectedKanjiList.isNotEmpty()) {
            if (kanjiCardClicked.value == true && translationCardClicked.value == true) {
                if (_selectedKanjiList.contains(kanjiId)) {
                    matchedCardsCounter++
                    _solvedKanjiList.add(kanjiId)
                    val copyList = _solvedKanjiList.toList()
                    // need to make a deep copy of list: if only list is referenced, it will not trigger recomposition
                    _solvedKanjis.value = copyList
                    kanjiCardClicked.value = false
                    translationCardClicked.value = false
                } else {
                    kanjiCardClicked.value = false
                    translationCardClicked.value = false
                }
            }
            _selectedKanjiList.clear()
            kanjiCardClicked.value = false
            translationCardClicked.value = false
        } else {
            _selectedKanjiList.add(kanjiId)
        }
    }
}