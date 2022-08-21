package by.godevelopment.kingcalculator.domain.playersdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import javax.inject.Inject

class GetPlayerTotalGamesUseCase @Inject constructor() {

    val TYPE_NAME = R.string.use_case_type_games

    operator fun invoke(parties: List<GameModel>): String {
        return parties.map { it.gameType }.size.toString()
    }
}
