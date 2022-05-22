package by.godevelopment.kingcalculator.data.repositories

import android.util.Log
import by.godevelopment.kingcalculator.commons.ROWS_NOT_INSERTED
import by.godevelopment.kingcalculator.commons.ROWS_NOT_UPDATED
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.data.datasource.PlayersDao
import by.godevelopment.kingcalculator.data.preference.KingPreferences
import by.godevelopment.kingcalculator.data.utils.toItemPlayerModel
import by.godevelopment.kingcalculator.data.utils.toPlayerCardModel
import by.godevelopment.kingcalculator.data.utils.toPlayerProfile
import by.godevelopment.kingcalculator.domain.models.ItemPlayerModel
import by.godevelopment.kingcalculator.domain.models.PlayerCardModel
import by.godevelopment.kingcalculator.domain.repositories.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayerRepositoriesImpl @Inject constructor(
    private val playersDao: PlayersDao,
    private val kingPreferences: KingPreferences
) : PlayerRepository {

    override fun getAllPlayers(): Flow<List<ItemPlayerModel>> =
        playersDao
            .getAllPlayerProfiles()
            .map { list ->
                list.map { it.toItemPlayerModel() }
            }

    override suspend fun getPlayerById(id: Int): PlayerCardModel? =
        playersDao.getPlayerProfileById(id)?.toPlayerCardModel()

    override suspend fun saveNewPlayer(params: PlayerCardModel): Boolean {
        val result = playersDao.insertPlayerProfile(params.toPlayerProfile())
        Log.i(TAG, "PlayerRepositoriesImpl saveNewPlayer: $params -> $result")
        return result != ROWS_NOT_INSERTED
    }

    override suspend fun updatePlayerById(params: PlayerCardModel): Boolean {
        val result = playersDao.updatePlayerProfile(params.toPlayerProfile())
        Log.i(TAG, "PlayerRepositoriesImpl updatePlayerId: $params -> $result")
        return result != ROWS_NOT_UPDATED
    }

    override suspend fun deletePlayerById(params: PlayerCardModel): Boolean {
        val result = playersDao.deletePlayerProfile(params.toPlayerProfile())
        Log.i(TAG, "PlayerRepositoriesImpl deletePlayerById: $params -> $result")
        return result != ROWS_NOT_UPDATED
    }
}
