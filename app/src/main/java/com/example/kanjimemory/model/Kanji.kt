package com.example.kanjimemory.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "kanji")
data class Kanji (

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name= "Kanjis")
    var kanji: String = "empty kanji",

    @ColumnInfo(name= "Translations")
    val translation: String = "empty translation",

    @ColumnInfo(name = "TranslationDate")
    var dateTranslated: Long = 0,

    @ColumnInfo(name = "Counter")
    var counter: Int = 0

    )