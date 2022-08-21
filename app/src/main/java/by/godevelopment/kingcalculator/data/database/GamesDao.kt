package by.godevelopment.kingcalculator.data.database

import androidx.room.*
import by.godevelopment.kingcalculator.data.entities.GameNote
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {

    @Query("DELETE FROM games")
    suspend fun deleteAll(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPartyNote(gameNote: GameNote): Long

    @Update
    suspend fun updatePartyNote(gameNote: GameNote): Int

    @Query("SELECT * FROM games ORDER BY id DESC")
    fun getAllGameNotes(): Flow<List<GameNote>>

    @Query("SELECT * from games WHERE partyId = :key")
    fun getFlowGameNotesByPartyId(key: Long): Flow<List<GameNote>>

    @Query("SELECT * from games WHERE partyId = :key")
    suspend fun getGameNotesByPartyId(key: Long): List<GameNote>

    @Query("SELECT * from games WHERE id = :key")
    suspend fun getGameNoteById(key: Long): GameNote?
}
