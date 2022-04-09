package by.godevelopment.kingcalculator.domain.repositories

import by.godevelopment.kingcalculator.domain.models.PlayerModel
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    suspend fun getPlayerById(): PlayerModel
    fun getPlayers(): Flow<PlayerModel>
}