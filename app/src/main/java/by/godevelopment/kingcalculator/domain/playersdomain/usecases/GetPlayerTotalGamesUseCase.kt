package by.godevelopment.kingcalculator.domain.playersdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import javax.inject.Inject

class GetPlayerTotalGamesUseCase @Inject constructor() {

    val gamesRes = R.string.use_case_type_games

    operator fun invoke(games: List<GameModel>): String {
        return games.map { it.gameType }.size.toString()
    }
}
