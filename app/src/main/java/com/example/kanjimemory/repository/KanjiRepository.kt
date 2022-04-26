package com.example.kanjimemory.repository

import com.example.kanjimemory.data.KanjiDatabaseDao
import com.example.kanjimemory.model.Kanji
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class KanjiRepository @Inject constructor(private val kanjiDatabaseDao: KanjiDatabaseDao){
    fun getAllKanjis(): Flow<List<Kanji>> = kanjiDatabaseDao.getAllData().flowOn(Dispatchers.IO).conflate()

}