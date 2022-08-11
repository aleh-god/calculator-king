package by.godevelopment.kingcalculator.data.datasource

import android.util.Log
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.ROWS_NOT_INSERTED
import by.godevelopment.kingcalculator.commons.ROWS_NOT_UPDATED
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.data.database.PlayersDao
import by.godevelopment.kingcalculator.data.utils.toPlayerModel
import by.godevelopment.kingcalculator.data.utils.toPlayerProfile
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.commons.models.wrapResultBy
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayersDataSource @Inject constructor(
    private val playersDao: PlayersDao
) {
    fun getAllPlayers(): Flow<List<PlayerModel>> =
        playersDao
            .getAllPlayerProfiles()
            .map { list ->
                list.map { it.toPlayerModel() }
            }

    suspend fun getPlayerModelByIdRaw(playerId: Long): PlayerModel? {
        Log.i(TAG, "getPlayerModelByIdRaw: playerId = $playerId")
        return playersDao.getPlayerProfileById(playerId)?.toPlayerModel()
    }

    suspend fun getPlayerModelById(playerId: Long): ResultDataBase<PlayerModel> {
        return wrapResultBy(playerId) { playersDao.getPlayerProfileById(it)?.toPlayerModel() }
    }

    suspend fun createPlayer(params: PlayerModel): ResultDataBase<Long> {
        return try {
            val result = playersDao.insertPlayerProfile(params.toPlayerProfile())
            if (result != ROWS_NOT_INSERTED) { ResultDataBase.Success(value = result) }
            else ResultDataBase.Error(message = R.string.message_error_data_save)
        } catch (e: Exception) {
            Log.i(TAG, "createPlayer: catch ${e.message}")
            ResultDataBase.Error(message = R.string.message_error_data_save)
        }
    }

    suspend fun updatePlayerById(params: PlayerModel): ResultDataBase<Int> {
        return try {
            val result = playersDao.updatePlayerProfile(params.toPlayerProfile())
            if (result != ROWS_NOT_UPDATED) { ResultDataBase.Success(value = result) }
            else ResultDataBase.Error(message = R.string.message_error_data_save)
        } catch (e: Exception) {
            Log.i(TAG, "updatePlayerById: catch ${e.message}")
            ResultDataBase.Error(message = R.string.message_error_data_save)
        }
    }

    suspend fun deletePlayerById(params: PlayerModel): ResultDataBase<Int> {
        return try {
            val result = playersDao.deletePlayerProfile(params.toPlayerProfile())
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
