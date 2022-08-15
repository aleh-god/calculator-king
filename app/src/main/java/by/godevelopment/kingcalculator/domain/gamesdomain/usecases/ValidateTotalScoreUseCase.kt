package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import by.godevelopment.kingcalculator.commons.BODY_ROW_TYPE
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel
import javax.inject.Inject

class ValidateTotalScoreUseCase @Inject constructor() {
    operator fun invoke(listItems: List<MultiItemModel>): Boolean {

        val typesCount = listItems
            .take(4)
            .map { it.gameType }
            .toSet()
            .size

        val gameType = listItems
            .first()
            .gameType

        val sumGame = listItems
            .filter { it.itemViewType == BODY_ROW_TYPE }
            .sumOf {
                it.gameType.getTotalGameScore(it.tricks)
            }

        fun checkSumByTypeGame(trickScores: Int): Boolean {
            return (sumGame == trickScores)
        }

        return if (typesCount > 1) {
            if (gameType.trickScores > 0 ) checkSumByTypeGame(GameType.TakeBFG.trickScores)
            else checkSumByTypeGame(GameType.DoNotTakeBFG.trickScores)
        }
        else checkSumByTypeGame(gameType.getTotalGameScore(gameType.tricksCount))
    }
}
