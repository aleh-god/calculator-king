package by.godevelopment.kingcalculator.domain.playersdomain.repositories

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.playersdomain.models.ItemPlayerModel
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    suspend fun getPlayerById(id: Long): PlayerModel?

    suspend fun createPlayer(params: PlayerModel): ResultDataBase<Long>

    suspend fun updatePlayerById(params: PlayerModel): ResultDataBase<Int>

    suspend fun deletePlayerById(params: PlayerModel): ResultDataBase<Int>

    suspend fun getAllPlayersNames(): List<String>

    fun getAllPlayers(): Flow<List<ItemPlayerModel>>
}