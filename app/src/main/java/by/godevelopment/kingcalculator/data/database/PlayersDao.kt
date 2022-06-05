package by.godevelopment.kingcalculator.data.database

import androidx.room.*
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayerProfile(player: PlayerProfile): Long

    @Update
    suspend fun updatePlayerProfile(player: PlayerProfile): Int

    @Delete
    suspend fun deletePlayerProfile(playerProfile: PlayerProfile): Int

    @Query("SELECT * FROM players ORDER BY id DESC")
    fun getAllPlayerProfiles(): Flow<List<PlayerProfile>>

    @Query("SELECT * FROM players ORDER BY id DESC")
    suspend fun getSuspendAllPlayerProfiles(): List<PlayerProfile>

    @Query("SELECT * from players WHERE email = :key")
    suspend fun getPlayerProfileByEmail(key: String): PlayerProfile?

    @Query("SELECT * from players WHERE id = :key")
    suspend fun getPlayerProfileById(key: Long): PlayerProfile?

    @Query("DELETE FROM players WHERE email = :key")
    suspend fun deletePlayerProfileByEmail(key: String): Int

}