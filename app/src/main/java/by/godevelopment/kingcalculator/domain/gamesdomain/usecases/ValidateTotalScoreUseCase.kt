package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import android.util.Log
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.BODY_ROW_TYPE
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.ValidationResult
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel
import javax.inject.Inject

class ValidateTotalScoreUseCase @Inject constructor() {
    fun invoke(listItems: List<MultiItemModel>): ValidationResult {

        val typesCount = listItems
            .take(3)
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

        fun checkSumByTypeGame(trickScores: Int): ValidationResult =
            if (sumGame == trickScores) ValidationResult(successful = true)
            else ValidationResult(
                successful = false,
                errorMessage = R.string.message_error_game_validate_not_equal_sum
            )

        Log.i(TAG, "ValidateTotalScoreUseCase: count = $typesCount type = $gameType sum = $sumGame")
        return if (typesCount > 1) {
            if (gameType.trickScores > 0 ) checkSumByTypeGame(GameType.TakeBFG.trickScores)
            else checkSumByTypeGame(GameType.DoNotTakeBFG.trickScores)
        }
        else checkSumByTypeGame(gameType.trickScores)
    }
}
