package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel
import javax.inject.Inject

class GetMultiItemModelsUseCase @Inject constructor() {

    suspend operator fun invoke(key: Long): List<MultiItemModel> {
        return listOf(
            MultiItemModel(
                rowId = 0,
                itemViewType = 0,
                headerText = "headerText + $key"
            ),
            MultiItemModel(
                rowId = 0,
                itemViewType = 1,
                gameTypesName = "gameTypesName",
                totalScore = 240,
                totalTricks = 8
            ),
            MultiItemModel(
                rowId = 0,
                itemViewType = 2,
                footerText = "footerText + $key"
            )
        )
    }
}