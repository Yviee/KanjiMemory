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
class KanjiViewModel @Inject constructor(private val repository: KanjiRepository): ViewModel() {

    // @HiltViewModel because ViewModel uses Hilt
    private val _kanjiList = MutableStateFlow<List<Kanji>> (emptyList())
    // initialise with/as empty List

    val kanjiList = _kanjiList.asStateFlow()
    // this is public API; holds stateful flow

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