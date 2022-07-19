package by.godevelopment.kingcalculator.domain.gamesdomain.repositories

import by.godevelopment.kingcalculator.data.models.PlayersInGameModel
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getGames(): Flow<GameModel>

    suspend fun getPlayersByGameId(key: Long): PlayersInGameModel

    suspend fun getGameTypeByGameId(key: Long): GameType
}