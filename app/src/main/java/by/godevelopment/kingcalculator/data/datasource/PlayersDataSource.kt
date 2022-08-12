package by.godevelopment.kingcalculator.data.datasource

import android.util.Log
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.ROWS_NOT_INSERTED
import by.godevelopment.kingcalculator.commons.ROWS_NOT_UPDATED
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.data.database.PlayersDao
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.commons.models.wrapResultBy
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayersDataSource @Inject constructor(
    private val playersDao: PlayersDao
) {
    fun getAllPlayers(): Flow<List<PlayerProfile>> = playersDao.getAllPlayerProfiles()

    suspend fun getPlayerProfileByIdRaw(playerId: Long): PlayerProfile? {
        Log.i(TAG, "getPlayerModelByIdRaw: playerId = $playerId")
        return playersDao.getPlayerProfileById(playerId)
    }

    suspend fun getPlayerProfileById(playerId: Long): ResultDataBase<PlayerProfile> {
        return wrapResultBy(playerId) { playersDao.getPlayerProfileById(it) }
    }

    suspend fun createPlayer(params: PlayerProfile): ResultDataBase<Long> {
        return try {
            val result = playersDao.insertPlayerProfile(params)
            if (result != ROWS_NOT_INSERTED) { ResultDataBase.Success(value = result) }
            else ResultDataBase.Error(message = R.string.message_error_data_save)
        } catch (e: Exception) {
            Log.i(TAG, "createPlayer: catch ${e.message}")
            ResultDataBase.Error(message = R.string.message_error_data_save)
        }
    }

    suspend fun updatePlayerById(params: PlayerProfile): ResultDataBase<Int> {
        return try {
            val result = playersDao.updatePlayerProfile(params)
            if (result != ROWS_NOT_UPDATED) { ResultDataBase.Success(value = result) }
            else ResultDataBase.Error(message = R.string.message_error_data_save)
        } catch (e: Exception) {
            Log.i(TAG, "updatePlayerById: catch ${e.message}")
            ResultDataBase.Error(message = R.string.message_error_data_save)
        }
    }

    suspend fun deletePlayerById(params: PlayerProfile): ResultDataBase<Int> {
        return try {
            val result = playersDao.deletePlayerProfile(params)
            if (result != ROWS_NOT_UPDATED) { ResultDataBase.Success(value = result) }
            else ResultDataBase.Error(message = R.string.message_error_data_save)
        } catch (e: Exception) {
            Log.i(TAG, "deletePlayerById: catch ${e.message}")
            ResultDataBase.Error(message = R.string.message_error_data_save)
        }
    }

    suspend fun getAllPlayersIdToNames(): Map<String, Long> {
        val result = playersDao.getSuspendAllPlayerProfiles()
        Log.i(TAG, "PlayerDataSource getAllPlayersEmailToNames: ${result.size}")
        return result.associate { it.name to it.id }
    }

    suspend fun getAllPlayersNames(): List<String> {
        val result = playersDao.getSuspendAllPlayerProfiles()
        Log.i(TAG, "PlayerDataSource getAllPlayersNames: ${result.size}")
        return result.map { it.name }
    }
}
