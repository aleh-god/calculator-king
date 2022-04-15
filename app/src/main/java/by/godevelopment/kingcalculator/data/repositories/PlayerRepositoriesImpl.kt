package by.godevelopment.kingcalculator.data.repositories

import android.util.Log
import by.godevelopment.kingcalculator.commons.ROWS_NOT_INSERTED
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.data.datasource.PlayersDao
import by.godevelopment.kingcalculator.data.preference.KingPreferences
import by.godevelopment.kingcalculator.domain.models.ItemPlayerModel
import by.godevelopment.kingcalculator.domain.models.PlayerCardModel
import by.godevelopment.kingcalculator.domain.repositories.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayerRepositoriesImpl @Inject constructor(
    private val playersDao: PlayersDao,
    private val kingPreferences: KingPreferences,
    private val utils: RepositoryUtils
) : PlayerRepository {

    override suspend fun getPlayerById(key: Int): PlayerCardModel {
        val result = playersDao.getPlayerProfileById(key)

        return TODO("getPlayerById")
    }

    override fun getAllPlayers(): Flow<List<ItemPlayerModel>> = playersDao
            .getAllPlayerProfiles()
            .map { list ->
                list.map { utils.convertPlayerFromProfileToItemModel(it) }
            }

    override suspend fun saveNewPlayer(params: PlayerCardModel): Boolean {
        val result = playersDao.insertPlayerProfile(
            utils.convertPlayerFromCardModelToProfile(params)
        )
        Log.i(TAG, "PlayerRepositoriesImpl saveNewPlayer: $params -> $result")
        return result != ROWS_NOT_INSERTED
    }
}
