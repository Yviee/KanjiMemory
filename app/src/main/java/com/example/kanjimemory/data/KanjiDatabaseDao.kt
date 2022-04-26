package com.example.kanjimemory.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
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

    /*
    @Query("SELECT * from kanji_db where id =:id")
    suspend fun getKanjiById(id: String): Kanji

    @Query("SELECT kanji from kanji_db")
    fun getKanjis(): Flow<List<Kanji>>

    @Query("SELECT translation from kanji_db")
    fun getTranslation(): Flow<List<Kanji>>

    /*
    @Insert
    suspend fun insertKanjis(kanji: List<Kanji>)
    // add Flow as in getKanjis() Function?



     */
     */
}
