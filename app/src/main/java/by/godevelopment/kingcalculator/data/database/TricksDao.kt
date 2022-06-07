package by.godevelopment.kingcalculator.data.database

import androidx.room.*
import by.godevelopment.kingcalculator.data.entities.TrickNote
import kotlinx.coroutines.flow.Flow

@Dao
interface TricksDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayerProfile(trick: TrickNote): Long

    @Update
    suspend fun updatePlayerProfile(trick: TrickNote): Int

    @Delete
    suspend fun deletePlayerProfile(trick: TrickNote): Int

    @Query("SELECT * FROM tricks ORDER BY id DESC")
    fun getAllTrickNotes(): Flow<List<TrickNote>>

    @Query("SELECT * FROM tricks ORDER BY id DESC")
    suspend fun getSuspendAllTrickNotes(): List<TrickNote>

    @Query("SELECT * from tricks WHERE id = :key")
    suspend fun getTrickNoteById(key: Long): TrickNote
}