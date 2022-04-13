package by.godevelopment.kingcalculator.domain.repositories

import by.godevelopment.kingcalculator.domain.models.ItemPlayerModel
import by.godevelopment.kingcalculator.domain.models.PlayerDataModel
import by.godevelopment.kingcalculator.domain.models.PlayerModel
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    suspend fun getPlayerById(): PlayerModel
    fun getPlayers(): Flow<List<ItemPlayerModel>>

    suspend fun saveNewPlayer(params: PlayerDataModel): Boolean
}