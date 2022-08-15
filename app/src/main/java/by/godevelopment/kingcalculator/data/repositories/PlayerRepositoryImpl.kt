package by.godevelopment.kingcalculator.data.repositories

import by.godevelopment.kingcalculator.data.datasource.PlayersDataSource
import by.godevelopment.kingcalculator.data.utils.toPlayerModel
import by.godevelopment.kingcalculator.data.utils.toPlayerProfile
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.commons.utils.mapResult
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import by.godevelopment.kingcalculator.domain.playersdomain.repositories.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playersDataSource: PlayersDataSource
) : PlayerRepository {

    override suspend fun getPlayerById(id: Long): ResultDataBase<PlayerModel> {
        return playersDataSource.getPlayerProfileById(id).mapResult {
            it.toPlayerModel()
        }
    }

    override suspend fun createPlayer(params: PlayerModel): ResultDataBase<Long> =
        playersDataSource.createPlayer(params.toPlayerProfile())

    override suspend fun updatePlayerById(params: PlayerModel): ResultDataBase<Int> =
        playersDataSource.updatePlayerById(params.toPlayerProfile())

    override suspend fun deletePlayerById(params: PlayerModel): ResultDataBase<Int> =
        playersDataSource.deletePlayerById(params.toPlayerProfile())

    override suspend fun getAllPlayersNames(): List<String> =
        playersDataSource.getAllPlayersNames()

    override fun getAllPlayers(): Flow<List<PlayerModel>> =
        playersDataSource
            .getAllPlayers()
            .map { list ->
                list.map { it.toPlayerModel() }
            }
}
