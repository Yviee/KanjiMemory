package com.example.kanjimemory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kanjimemory.model.Kanji

@Database(entities = [Kanji::class], version = 1, exportSchema = false)
abstract class KanjiDatabase: RoomDatabase() {
    // main access point to application's persistent data, extends RoomDatabase

    abstract fun kanjiDao(): KanjiDatabaseDao


}