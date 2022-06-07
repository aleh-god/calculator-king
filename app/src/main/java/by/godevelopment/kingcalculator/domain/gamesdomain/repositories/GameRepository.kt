package by.godevelopment.kingcalculator.domain.gamesdomain.repositories

import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getGames(): Flow<GameModel>
}