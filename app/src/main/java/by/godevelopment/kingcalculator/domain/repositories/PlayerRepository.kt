package by.godevelopment.kingcalculator.domain.repositories

import by.godevelopment.kingcalculator.domain.models.ItemPlayerModel
import by.godevelopment.kingcalculator.domain.models.PlayerCardModel
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    suspend fun getPlayerById(id: Long): PlayerCardModel?

    suspend fun saveNewPlayer(params: PlayerCardModel): Boolean

    suspend fun updatePlayerById(params: PlayerCardModel): Boolean

    suspend fun deletePlayerById(params: PlayerCardModel): Boolean

    suspend fun getAllPlayersNames(): List<String>

    fun getAllPlayers(): Flow<List<ItemPlayerModel>>
}