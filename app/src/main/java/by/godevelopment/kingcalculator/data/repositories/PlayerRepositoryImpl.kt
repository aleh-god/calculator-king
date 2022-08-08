package by.godevelopment.kingcalculator.data.repositories

import by.godevelopment.kingcalculator.data.datasource.PlayersDataSource
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.playersdomain.models.ItemPlayerModel
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import by.godevelopment.kingcalculator.domain.playersdomain.repositories.PlayerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playersDataSource: PlayersDataSource
) : PlayerRepository {
    override suspend fun getPlayerById(id: Long): PlayerModel? {
        return playersDataSource.getPlayerModelByIdRaw(id)
    }

    override suspend fun createPlayer(params: PlayerModel): ResultDataBase<Long> =
        playersDataSource.createPlayer(params)

    override suspend fun updatePlayerById(params: PlayerModel): ResultDataBase<Int> =
        playersDataSource.updatePlayerById(params)

    override suspend fun deletePlayerById(params: PlayerModel): ResultDataBase<Int> =
        playersDataSource.deletePlayerById(params)

    override suspend fun getAllPlayersNames(): List<String> =
        playersDataSource.getAllPlayersNames()

    override fun getAllPlayers(): Flow<List<ItemPlayerModel>> =
        playersDataSource.getAllPlayers()
}

