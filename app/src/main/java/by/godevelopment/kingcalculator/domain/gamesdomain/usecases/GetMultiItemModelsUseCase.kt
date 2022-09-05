package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import by.godevelopment.kingcalculator.commons.BODY_ROW_TYPE
import by.godevelopment.kingcalculator.commons.FOOTER_ROW_TYPE
import by.godevelopment.kingcalculator.commons.HEADER_ROW_TYPE
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GetMultiItemModelsRepository
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GetPartyIdByGameIdRepository
import javax.inject.Inject

class GetMultiItemModelsUseCase @Inject constructor(
    private val getMultiItemModelsRepository: GetMultiItemModelsRepository,
    private val getPartyIdByGameIdRepository: GetPartyIdByGameIdRepository
) {
    suspend operator fun invoke(gameId: Long): ResultDataBase<List<MultiItemModel>> {
        return when(val partyId = getPartyIdByGameIdRepository.getPartyIdByGameId(gameId)) {
            is ResultDataBase.Error -> { ResultDataBase.Error(message = partyId.message) }
            is ResultDataBase.Success -> {
                val playersResult = getMultiItemModelsRepository.getPlayersByPartyId(partyId.value)
                when (playersResult) {
                    is ResultDataBase.Error -> { ResultDataBase.Error(message = playersResult.message) }
                    is ResultDataBase.Success -> {

                        val players = playersResult.value
                            .toList()
                            .sortedBy { it.first.id }

                        val gameNoteResult = getMultiItemModelsRepository.getGameNoteById(gameId)
                        when(gameNoteResult) {
                            is ResultDataBase.Error -> { ResultDataBase.Error(message = gameNoteResult.message) }
                            is ResultDataBase.Success -> {

                                val gameType = gameNoteResult.value.gameType
                                val multiItems = when(gameType) {

                                    GameType.TakeBFG -> {
                                        var countId = 0
                                        val listItems = mutableListOf<MultiItemModel>()
                                        players.forEach { (players, playerModel) ->
                                            listItems.add(
                                                MultiItemModel(
                                                    rowId = countId++,
                                                    itemViewType = HEADER_ROW_TYPE,
                                                    player = playerModel,
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
                                                            player = playerModel,
                                                            playerNumber = players,
                                                            gameType = it
                                                        )
                                                    )
                                                }
                                            listItems.add(
                                                MultiItemModel(
                                                    rowId = countId++,
                                                    itemViewType = FOOTER_ROW_TYPE,
                                                    player = playerModel,
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
                                        players.forEach { (players, playerModel) ->
                                            listItems.add(
                                                MultiItemModel(
                                                    rowId = countId++,
                                                    itemViewType = HEADER_ROW_TYPE,
                                                    player = playerModel,
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
                                                            player = playerModel,
                                                            playerNumber = players,
                                                            gameType = it
                                                        )
                                                    )
                                                }
                                            listItems.add(
                                                MultiItemModel(
                                                    rowId = countId++,
                                                    itemViewType = FOOTER_ROW_TYPE,
                                                    player = playerModel,
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
                                        players.forEach { (players, playerModel) ->
                                            listItems.add(
                                                MultiItemModel(
                                                    rowId = countId++,
                                                    itemViewType = HEADER_ROW_TYPE,
                                                    player = playerModel,
                                                    playerNumber = players,
                                                    gameType = gameType
                                                )
                                            )
                                            listItems.add(
                                                MultiItemModel(
                                                    rowId = countId++,
                                                    itemViewType = BODY_ROW_TYPE,
                                                    player = playerModel,
                                                    playerNumber = players,
                                                    gameType = gameType
                                                )
                                            )
                                            listItems.add(
                                                MultiItemModel(
                                                    rowId = countId++,
                                                    itemViewType = FOOTER_ROW_TYPE,
                                                    player = playerModel,
                                                    playerNumber = players,
                                                    gameType = gameType
                                                )
                                            )
                                        }
                                        listItems
                                    }
                                }
                                ResultDataBase.Success(value = multiItems)
                            }
                        }
                    }
                }
            }
        }
    }
}
