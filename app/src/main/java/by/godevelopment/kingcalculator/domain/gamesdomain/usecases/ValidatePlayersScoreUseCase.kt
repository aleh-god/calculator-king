package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.BODY_ROW_TYPE
import by.godevelopment.kingcalculator.domain.gamesdomain.models.ListValidationResult
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel
import javax.inject.Inject

class ValidatePlayersScoreUseCase @Inject constructor(
    private val validateTotalScoreUseCase: ValidateTotalScoreUseCase
) {

    operator fun invoke(items: List<MultiItemModel>): ListValidationResult {
        val validateResult = validateTotalScoreUseCase(items)
        return if (validateResult) {
            val result = items.toMutableList()
            val body = items.filter { it.itemViewType == BODY_ROW_TYPE }

            body.forEach { model ->
                val game = model.gameType
                val sumTricks = body.filter { it.gameType == game }.sumOf { it.tricks }
                val check = (sumTricks != 0 && sumTricks != game.tricksCount)
                result[model.rowId] = result[model.rowId].copy(hasError = check)
            }
            result.firstOrNull { it.hasError }?.let {
                return ListValidationResult(
                    successful = false,
                    errorList = result,
                    errorMessage = R.string.message_error_input_values
                )
            }
            ListValidationResult(
                successful = true,
                errorList = result
            )
        } else {
            ListValidationResult(
                successful = false,
                errorMessage = R.string.message_error_game_validate_not_equal_sum
            )
        }
    }
}
