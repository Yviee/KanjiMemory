package com.example.kanjimemory.viewmodel

import android.util.Log
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
class RepetitionViewModel @Inject constructor(private val repository: KanjiRepository): ViewModel() {

    private val _kanjiList = MutableStateFlow<List<Kanji>> (emptyList())

    val kanjiList = _kanjiList.asStateFlow()

    // in dao: write function for incoming of all random kanjis

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllKanjis().distinctUntilChanged().collect { listOfKanjis ->
                if(listOfKanjis.isNullOrEmpty()) {
                    Log.d("databasetryout","No kanjis in your database!")
                } else {
                    _kanjiList.value = listOfKanjis
                }
            }
        }
    }
}