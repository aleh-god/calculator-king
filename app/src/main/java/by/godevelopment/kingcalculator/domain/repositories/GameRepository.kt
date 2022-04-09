package by.godevelopment.kingcalculator.domain.repositories

import by.godevelopment.kingcalculator.domain.models.GameModel
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getGames(): Flow<GameModel>
}