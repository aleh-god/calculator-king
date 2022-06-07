package by.godevelopment.kingcalculator.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.godevelopment.kingcalculator.data.entities.GameNote
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPartyNote(partyNote: GameNote): Long

    @Query("SELECT * FROM games ORDER BY id DESC")
    fun getAllGameNotes(): Flow<List<GameNote>>

    @Query("SELECT * from games WHERE partyId = :key")
    suspend fun getGameNotesByPartyId(key: Long): List<GameNote>

    @Query("SELECT * from games WHERE id = :key")
    suspend fun getGameNoteById(key: Long): GameNote?
}