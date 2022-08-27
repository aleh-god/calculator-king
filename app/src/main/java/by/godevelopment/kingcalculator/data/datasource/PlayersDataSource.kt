package by.godevelopment.kingcalculator.data.datasource

import android.util.Log
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.ROWS_NOT_INSERTED
import by.godevelopment.kingcalculator.commons.ROWS_NOT_UPDATED
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.data.database.PlayersDao
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.commons.utils.wrapResult
import by.godevelopment.kingcalculator.domain.commons.utils.wrapResultBy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayersDataSource @Inject constructor(
    private val playersDao: PlayersDao
) {
    fun getAllPlayers(): Flow<List<PlayerProfile>> = playersDao.getAllPlayerProfiles()

    suspend fun getPlayerProfileByIdRaw(playerId: Long): PlayerProfile? =
        playersDao.getPlayerProfileById(playerId)

    suspend fun getActivePlayerProfileByIdRaw(playerId: Long): PlayerProfile? =
        playersDao.getPlayerProfileById(playerId)?.takeIf { it.isActive }

    suspend fun getPlayerProfileById(playerId: Long): ResultDataBase<PlayerProfile> =
        wrapResultBy(playerId) { playersDao.getPlayerProfileById(it) }

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

    suspend fun disablePlayerById(params: PlayerProfile): ResultDataBase<Int> {
        return try {
            val disableResult = playersDao.updatePlayerProfile(params.copy(isActive = false))
            if (disableResult != ROWS_NOT_UPDATED) { ResultDataBase.Success(value = disableResult) }
            else ResultDataBase.Error(message = R.string.message_error_data_save)
        } catch (e: Exception) {
            Log.i(TAG, "deletePlayerById: catch ${e.message}")
            ResultDataBase.Error(message = R.string.message_error_data_save)
        }
    }

    suspend fun getAllActivePlayersIdToNames(): Map<String, Long> =
        playersDao.getSuspendAllPlayerProfiles()
            .filter { it.isActive }
            .associate { it.name to it.id }

    suspend fun getAllActivePlayersNames(): List<String> =
        playersDao.getSuspendAllPlayerProfiles()
            .filter { it.isActive }
            .map { it.name }

    suspend fun deleteAllPlayers(): ResultDataBase<Int> =
        wrapResult { playersDao.deleteAll() }
}
