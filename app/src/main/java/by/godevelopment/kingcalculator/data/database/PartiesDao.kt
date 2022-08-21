package by.godevelopment.kingcalculator.data.database

import androidx.room.*
import by.godevelopment.kingcalculator.data.entities.PartyNote
import kotlinx.coroutines.flow.Flow

@Dao
interface PartiesDao {

    @Query("DELETE FROM parties")
    suspend fun deleteAll(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPartyNote(partyNote: PartyNote): Long

    @Update
    suspend fun updatePartyNote(partyNote: PartyNote): Int

    @Query("SELECT * FROM parties ORDER BY id DESC")
    fun getAllPartyNotes(): Flow<List<PartyNote>>

    @Query("SELECT * from parties WHERE id = :key")
    suspend fun getPartyNoteById(key: Long): PartyNote?

    @Query("SELECT * from parties WHERE playerOneId = :key OR playerTwoId = :key OR playerThreeId = :key OR playerFourId = :key")
    fun getAllPartiesListByPlayerId(key: Long): List<PartyNote>
}
