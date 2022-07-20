package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.gamesdomain.models.BodyItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.FooterItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.HeaderItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel
import javax.inject.Inject

class GetMultiItemModelsUseCase @Inject constructor(
    private val getPlayersByGameIdUseCase: GetPlayersByGameIdUseCase,
    private val getGameTypeByGameIdUseCase: GetGameTypeByGameIdUseCase
) {

    suspend operator fun invoke(key: Long): List<MultiItemModel> {
        val dbData = getPlayersByGameIdUseCase.invoke(key)
        val gameType = getGameTypeByGameIdUseCase.invoke(key)
        val res: List<MultiItemModel> = when(gameType) {
            GameType.TakeBFG, GameType.DoNotTakeBFG -> {
                var countId = 0
                val listItems = mutableListOf<MultiItemModel>()
                        dbData.playersMap.forEach { (players, playerProfile) ->
                            listItems.add(
                                HeaderItemModel(
                                    rowId = countId++,
                                    player = playerProfile,
                                    playerNumberRes = players.res
                                )
                            )
                            GameType.values()
                                .filter { it != GameType.DoNotTakeBFG && it != GameType.TakeBFG }
                                .forEach {
                                    listItems.add(
                                        BodyItemModel(rowId = countId++, gameType = it)
                                    )
                                }
                            listItems.add(FooterItemModel(rowId = countId++))
                        }
                listItems
            }
            else -> {
                var countId = 0
                val listItems = mutableListOf<MultiItemModel>()
                dbData.playersMap.forEach { (players, playerProfile) ->
                    listItems.add(
                        HeaderItemModel(
                            rowId = countId++,
                            player = playerProfile,
                            playerNumberRes = players.res
                        )
                    )
                    listItems.add(
                        BodyItemModel(rowId = countId++, gameType = gameType)
                    )
                    listItems.add(FooterItemModel(rowId = countId++))
                }
                listItems
            }
        }
        return res
    }
}
