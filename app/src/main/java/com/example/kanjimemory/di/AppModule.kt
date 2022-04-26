package com.example.kanjimemory.di

import android.content.Context
import androidx.room.Room
import com.example.kanjimemory.data.KanjiDatabase
import com.example.kanjimemory.data.KanjiDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    // Module needed to add binding to Hilt (tell Hilt how to provide instances of different types)
    // SingletonComponent to have only 1 source of truth
    // This provides a single instance of DAO (data access object) and the actual Database
    // These functions will not be invoked, they run in the background and are used by Hilt libraries.

    @Singleton
    @Provides
    fun provideKanjiDao(kanjiDatabase: KanjiDatabase): KanjiDatabaseDao = kanjiDatabase.kanjiDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): KanjiDatabase
    = Room.databaseBuilder(
        context,
        KanjiDatabase::class.java,
        "kanji"
    ).fallbackToDestructiveMigration()
        .createFromAsset("kanji.db")
        .build()

    // creates Room Database using KanjiDatabase, which tells function how Database is to be constructed
}