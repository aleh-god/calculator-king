package by.godevelopment.kingcalculator.data.repositories

import by.godevelopment.kingcalculator.data.database.GamesDao
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gamesDao: GamesDao
) {
}