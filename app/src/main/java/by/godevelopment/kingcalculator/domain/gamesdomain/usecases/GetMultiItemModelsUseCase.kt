package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import by.godevelopment.kingcalculator.commons.BODY_ROW_TYPE
import by.godevelopment.kingcalculator.commons.FOOTER_ROW_TYPE
import by.godevelopment.kingcalculator.commons.HEADER_ROW_TYPE
import by.godevelopment.kingcalculator.domain.commons.models.GameType
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
            GameType.TakeBFG -> {
                var countId = 0
                val listItems = mutableListOf<MultiItemModel>()
                dbData.playersMap
                    .toList()
                    .sortedBy {
                        it.first.id
                    }
                    .forEach { (players, playerProfile) ->
                        listItems.add(
                            MultiItemModel(
                                rowId = countId++,
                                itemViewType = HEADER_ROW_TYPE,
                                player = playerProfile,
                                playerNumber = players,
                                gameType = gameType
                            )
                        )
                        GameType
                            .values()
                            .filter { it.id < GameType.TakeBFG.id }
                            .forEach {
                                listItems.add(
                                    MultiItemModel(
                                        rowId = countId++,
                                        itemViewType = BODY_ROW_TYPE,
                                        player = playerProfile,
                                        playerNumber = players,
                                        gameType = it
                                    )
                                )
                            }
                        listItems.add(
                            MultiItemModel(
                                rowId = countId++,
                                itemViewType = FOOTER_ROW_TYPE,
                                player = playerProfile,
                                playerNumber = players,
                                gameType = gameType
                            )
                        )
                    }
                listItems
            }
            GameType.DoNotTakeBFG -> {
                var countId = 0
                val listItems = mutableListOf<MultiItemModel>()
                dbData.playersMap
                    .toList()
                    .sortedBy {
                        it.first.id
                    }
                    .forEach { (players, playerProfile) ->
                        listItems.add(
                            MultiItemModel(
                                rowId = countId++,
                                itemViewType = HEADER_ROW_TYPE,
                                player = playerProfile,
                                playerNumber = players,
                                gameType = gameType
                            )
                        )
                        GameType
                            .values()
                            .filter { GameType.TakeBFG.id < it.id && it.id < GameType.DoNotTakeBFG.id }
                            .forEach {
                                listItems.add(
                                    MultiItemModel(
                                        rowId = countId++,
                                        itemViewType = BODY_ROW_TYPE,
                                        player = playerProfile,
                                        playerNumber = players,
                                        gameType = it
                                    )
                                )
                            }
                        listItems.add(
                            MultiItemModel(
                                rowId = countId++,
                                itemViewType = FOOTER_ROW_TYPE,
                                player = playerProfile,
                                playerNumber = players,
                                gameType = gameType
                            )
                        )
                    }
                listItems
            }
            else -> {
                var countId = 0
                val listItems = mutableListOf<MultiItemModel>()
                dbData.playersMap
                    .toList()
                    .sortedBy { it.first.id }
                    .forEach { (players, playerProfile) ->
                        listItems.add(
                            MultiItemModel(
                                rowId = countId++,
                                itemViewType = HEADER_ROW_TYPE,
                                player = playerProfile,
                                playerNumber = players,
                                gameType = gameType
                            )
                        )
                        listItems.add(
                            MultiItemModel(
                                rowId = countId++,
                                itemViewType = BODY_ROW_TYPE,
                                player = playerProfile,
                                playerNumber = players,
                                gameType = gameType
                            )
                        )
                        listItems.add(
                            MultiItemModel(
                                rowId = countId++,
                                itemViewType = FOOTER_ROW_TYPE,
                                player = playerProfile,
                                playerNumber = players,
                                gameType = gameType
                            )
                        )
                    }
                listItems
            }
        }
        return res
    }
}
