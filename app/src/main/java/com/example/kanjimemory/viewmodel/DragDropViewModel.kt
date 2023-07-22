package com.example.kanjimemory.viewmodel

import android.util.Log
import androidx.compose.runtime.*
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
class DragDropViewModel @Inject constructor(private val repository: KanjiRepository): ViewModel() {

    private val _randomKanjiList = MutableStateFlow<List<Kanji>> (emptyList())
    val randomKanjiList = _randomKanjiList.asStateFlow()

    // TODO: try collecting asLiveData()
    // try: https://developer.android.com/codelabs/advanced-kotlin-coroutines#11

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

    val displayToast: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }

    // private set to only be able to change value inside of viewModel
    var isCurrentlyDragging by mutableStateOf(false)
        private set

    var items by mutableStateOf(emptyList<Kanji>())
        private set

    var addedKanji = mutableStateListOf<Kanji>()
        private set


    fun startDragging(){
        isCurrentlyDragging = true
    }
    fun stopDragging(){
        isCurrentlyDragging = false
    }

    fun addKanji(kanji: Kanji){
        println("Added kanji: $kanji")
        addedKanji.add(kanji)
    }

    fun checkIfMatch(kanji: Kanji) {
        if (kanji == randomKanjiList.value.first()) {
            displayToast.value = "Congrats, you got it! ^_^"
            getRandomKanjis()
        } else {
            displayToast.value = "Sorry, try again with another kanji."
        }
    }
}