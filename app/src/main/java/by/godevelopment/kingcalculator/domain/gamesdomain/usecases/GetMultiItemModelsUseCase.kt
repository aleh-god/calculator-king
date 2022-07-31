package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import android.util.Log
import by.godevelopment.kingcalculator.commons.BODY_ROW_TYPE
import by.godevelopment.kingcalculator.commons.FOOTER_ROW_TYPE
import by.godevelopment.kingcalculator.commons.HEADER_ROW_TYPE
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel
import java.lang.IllegalStateException
import javax.inject.Inject

class GetMultiItemModelsUseCase @Inject constructor(
    private val getPlayersByPartyIdUseCase: GetPlayersByPartyIdUseCase,
    private val getPartyIdByGameIdUseCase: GetPartyIdByGameIdUseCase,
    private val getGameNoteByIdUseCase: GetGameNoteByIdUseCase
) {
    suspend operator fun invoke(gameId: Long): List<MultiItemModel> {
        Log.i(TAG, "GetMultiItemModelsUseCase gameId: $gameId")
        when(val partyId = getPartyIdByGameIdUseCase(gameId)) {
            is ResultDataBase.Error -> {
                // TODO("change Exception to ResultDataBase.Error")
                throw IllegalStateException()
            }
            is ResultDataBase.Success -> {
                when (val playersResult = getPlayersByPartyIdUseCase(partyId.value)) {
                    is ResultDataBase.Error -> { throw IllegalStateException() }
                    is ResultDataBase.Success -> {

                        val players = playersResult.value
                            .playersMap
                            .toList()
                            .sortedBy { it.first.id }

                        when(val gameNoteResult = getGameNoteByIdUseCase(gameId)) {
                            is ResultDataBase.Error -> { throw IllegalStateException() }
                            is ResultDataBase.Success -> {
                                return when(val gameType = gameNoteResult.value.gameType) {
                                    GameType.TakeBFG -> {
                                        var countId = 0
                                        val listItems = mutableListOf<MultiItemModel>()
                                        players.forEach { (players, playerProfile) ->
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
                                        players.forEach { (players, playerProfile) ->
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
                                        players.forEach { (players, playerProfile) ->
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
                            }
                        }
                    }
                }
            }
        }
    }
}
