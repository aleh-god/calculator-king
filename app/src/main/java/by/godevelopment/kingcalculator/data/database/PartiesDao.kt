package by.godevelopment.kingcalculator.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.godevelopment.kingcalculator.data.entities.PartyNote
import kotlinx.coroutines.flow.Flow

@Dao
interface PartiesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPartyNote(partyNote: PartyNote): Long

    @Query("SELECT * FROM parties ORDER BY id DESC")
    fun getAllPartyNotes(): Flow<List<PartyNote>>

    @Query("SELECT * from parties WHERE id = :key")
    suspend fun getPartyNoteById(key: Int): PartyNote?

    @Query("SELECT * from parties WHERE playerOneId = :key OR playerTwoId = :key OR playerThreeId = :key OR playerFourId = :key")
    fun getAllPartyNotesByPlayerId(key: Int): Flow<List<PartyNote>>
}