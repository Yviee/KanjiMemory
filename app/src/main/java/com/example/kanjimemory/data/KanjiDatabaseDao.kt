package com.example.kanjimemory.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.kanjimemory.model.Kanji
import kotlinx.coroutines.flow.Flow

@Dao
interface KanjiDatabaseDao {
    // Dao = Data Access Object. Accesses the actual database.
    // Functions here will be implemented elsewhere.
    // Since we only want the user to fetch/read data, but not alter the database in any way,
    // no delete, update or insert functions are needed.
    // To use Coroutines (in order to have processes run asynchronously), we use the suspend keyword.
    // Flow: asynchronous data stream that sequentially emits values

    @Query("SELECT * FROM kanji")
    fun getAllData(): Flow<List<Kanji>>

    @Query("SELECT * FROM kanji WHERE id IN (SELECT id FROM kanji ORDER BY RANDOM() LIMIT 5)")
    fun getRandomKanjis(): Flow<List<Kanji>>

    // get single random Kanji object
    @Query("SELECT * FROM kanji WHERE id IN (SELECT id FROM kanji ORDER BY RANDOM() LIMIT 1)")
    fun getOneRandomKanji(): Flow<Kanji>

    @Update
    suspend fun update(kanji: Kanji)

    // supposed to be more performant than just: "select * from kanji order by random() limit 1"
    // see: https://stackoverflow.com/questions/4114940/select-random-rows-in-sqlite

}
