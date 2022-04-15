package by.godevelopment.kingcalculator.data.datasource

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

    @Query("SELECT * from players WHERE email = :key")
    suspend fun getPlayerProfileByEmail(key: String): PlayerProfile?

    @Query("SELECT * from players WHERE id = :key")
    suspend fun getPlayerProfileById(key: Int): PlayerProfile?

    @Query("DELETE FROM players WHERE email = :key")
    suspend fun deletePlayerProfileByEmail(key: String): Int

}