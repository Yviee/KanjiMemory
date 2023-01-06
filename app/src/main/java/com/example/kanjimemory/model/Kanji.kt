package com.example.kanjimemory.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "kanji")
data class Kanji (

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name= "Kanjis")
    val kanji: String,

    @ColumnInfo(name= "Translations")
    val translation: String,

    @ColumnInfo(name = "TranslationDate")
    var dateTranslated: Long,

    @ColumnInfo(name = "Counter")
    var counter: Int = 0

    /*@Ignore
    val enabled: Boolean = true*/

    // not sure if ColumnInfo still needed since db already has named columns...
    )