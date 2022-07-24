package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import by.godevelopment.kingcalculator.commons.BODY_ROW_TYPE
import by.godevelopment.kingcalculator.domain.commons.models.ValidationResult
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel
import javax.inject.Inject

class ValidatePlayersScoreUseCase @Inject constructor() {
    fun invoke(listItems: List<MultiItemModel>): ValidationResult {

        val list = listItems
            .filter { it.itemViewType == BODY_ROW_TYPE }
            .associateBy {
                it.gameType
            }

        return TODO()
    }
}