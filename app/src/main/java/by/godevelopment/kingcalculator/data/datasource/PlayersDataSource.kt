package by.godevelopment.kingcalculator.data.datasource

import android.util.Log
import by.godevelopment.kingcalculator.commons.DELETED_STRING_VALUE
import by.godevelopment.kingcalculator.commons.ROWS_NOT_INSERTED
import by.godevelopment.kingcalculator.commons.ROWS_NOT_UPDATED
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.data.database.PlayersDao
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.data.utils.toItemPlayerModel
import by.godevelopment.kingcalculator.data.utils.toPlayerCardModel
import by.godevelopment.kingcalculator.data.utils.toPlayerProfile
import by.godevelopment.kingcalculator.domain.playersdomain.models.ItemPlayerModel
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerCardModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayersDataSource @Inject constructor(
    private val playersDao: PlayersDao
) {
    fun getAllPlayers(): Flow<List<ItemPlayerModel>> =
        playersDao
            .getAllPlayerProfiles()
            .map { list ->
                list.map { it.toItemPlayerModel() }
            }

    suspend fun getPlayerById(id: Long): PlayerCardModel? =
        playersDao.getPlayerProfileById(id)?.toPlayerCardModel()

    suspend fun getPlayerProfileById(playerId: Long): PlayerProfile {
        return playersDao.getPlayerProfileById(playerId) ?:
        PlayerProfile(
            name = DELETED_STRING_VALUE,
            email = DELETED_STRING_VALUE,
            avatar = 0,
            color = 0
        )
    }

    suspend fun saveNewPlayer(params: PlayerCardModel): Boolean {
        val result = playersDao.insertPlayerProfile(params.toPlayerProfile())
        Log.i(TAG, "PlayerDataSource saveNewPlayer: $params -> $result")
        return result != ROWS_NOT_INSERTED
    }

    suspend fun updatePlayerById(params: PlayerCardModel): Boolean {
        val result = playersDao.updatePlayerProfile(params.toPlayerProfile())
        Log.i(TAG, "PlayerDataSource updatePlayerId: $params -> $result")
        return result != ROWS_NOT_UPDATED
    }

    suspend fun deletePlayerById(params: PlayerCardModel): Boolean {
        val result = playersDao.deletePlayerProfile(params.toPlayerProfile())
        Log.i(TAG, "PlayerDataSource deletePlayerById: $params -> $result")
        return result != ROWS_NOT_UPDATED
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
