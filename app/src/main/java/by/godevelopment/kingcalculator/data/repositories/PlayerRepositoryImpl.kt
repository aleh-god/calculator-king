package by.godevelopment.kingcalculator.data.repositories

import by.godevelopment.kingcalculator.data.datasource.PlayerDataSource
import by.godevelopment.kingcalculator.domain.models.ItemPlayerModel
import by.godevelopment.kingcalculator.domain.models.PlayerCardModel
import by.godevelopment.kingcalculator.domain.repositories.PlayerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playersDataSource: PlayerDataSource
) : PlayerRepository {
    override suspend fun getPlayerById(id: Long): PlayerCardModel? {
        return playersDataSource.getPlayerById(id)
    }

    override suspend fun saveNewPlayer(params: PlayerCardModel): Boolean =
        playersDataSource.saveNewPlayer(params)

    override suspend fun updatePlayerById(params: PlayerCardModel): Boolean =
        playersDataSource.updatePlayerById(params)

    override suspend fun deletePlayerById(params: PlayerCardModel): Boolean =
        playersDataSource.deletePlayerById(params)

    override suspend fun getAllPlayersNames(): List<String> =
        playersDataSource.getAllPlayersNames()

    override fun getAllPlayers(): Flow<List<ItemPlayerModel>> =
        playersDataSource.getAllPlayers()
}

