package by.godevelopment.kingcalculator.data.database

import androidx.room.*
import by.godevelopment.kingcalculator.data.entities.TricksNote
import kotlinx.coroutines.flow.Flow

@Dao
interface TricksDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTricksNote(trick: TricksNote): Long

    @Update
    suspend fun updateTricksNote(trick: TricksNote): Int

    @Delete
    suspend fun deleteTricksNote(trick: TricksNote): Int

    @Query("SELECT * FROM tricks ORDER BY id DESC")
    fun getAllTricksNotes(): Flow<List<TricksNote>>

    @Query("SELECT * from tricks WHERE playerId = :key")
    suspend fun getTricksNotesByPlayerId(key: Long): List<TricksNote>

    @Query("SELECT * from tricks WHERE id = :key")
    suspend fun getTricksNoteById(key: Long): TricksNote?

    @Query("SELECT * from tricks WHERE gameId = :key")
    suspend fun getTricksNotesByGameId(key: Long): List<TricksNote>
}