package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import android.util.Log
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.partiesdomain.models.PartyInfoItemModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import javax.inject.Inject

class GetGamesScoreByPartyIdUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {

    suspend operator fun invoke(partyId: Long): ResultDataBase<List<PartyInfoItemModel>> {
        Log.i(TAG, "GetGamesByPartyIdUseCase.invoke: $partyId")

        val listResult = partyRepository.getAllGamesNotesByPartyId(partyId)
        return when(listResult) {
            is ResultDataBase.Error -> ResultDataBase.Error(message = listResult.message)
            is ResultDataBase.Success -> {

                val gamesNotes = listResult
                    .value
                    .sortedBy { it.gameType.id }

                Log.i(TAG, "GetGamesByPartyIdUseCase.invoke: gamesNotes = ${gamesNotes.size}")

                var index: Int = 0
                val itemModels: List<PartyInfoItemModel> = GameType
                    .values()
                    .map { type ->

                        val game = gamesNotes.firstOrNull() { type == it.gameType }
                        Log.i(TAG, "List<PartyInfoItemModel>: index = $index type = ${type.name} game = $game")

                        if (game != null) {
                            val partyResult = partyRepository.getPartyModelById(game.partyId)
                            when(partyResult) {
                                is ResultDataBase.Error -> return@map PartyInfoItemModel(
                                    id = index++,
                                    gameType = type
                                )
                                is ResultDataBase.Success -> {

                                    val tricksResult = partyRepository.getAllTricksNotesByGameId(game.id)
                                    Log.i(TAG, "List<PartyInfoItemModel>: tricksResult = ${tricksResult.size}")

                                    val party = partyResult.value
                                    Log.i(TAG, "List<PartyInfoItemModel>: party = $party")

                                    PartyInfoItemModel(
                                        id = index++,
                                        gameType = type,
                                        oneGameScore = tricksResult
                                            .filter { it.playerId == party.playerOneId }
                                            .sumOf { type.getTotalGameScore(it.tricksCount) },
                                        twoGameScore = tricksResult
                                            .filter { it.playerId == party.playerTwoId }
                                            .sumOf { type.getTotalGameScore(it.tricksCount) },
                                        threeGameScore = tricksResult
                                            .filter { it.playerId == party.playerThreeId }
                                            .sumOf { type.getTotalGameScore(it.tricksCount) },
                                        fourGameScore = tricksResult
                                            .filter { it.playerId == party.playerFourId }
                                            .sumOf { type.getTotalGameScore(it.tricksCount) }
                                    )
                                }
                            }
                        }
                        else {
                            Log.i(TAG, "List<PartyInfoItemModel>:else index = $index")
                            PartyInfoItemModel(
                                id = index++,
                                gameType = type
                            )
                        }
                    }
                Log.i(TAG, "List<PartyInfoItemModel>: itemModels ${itemModels.size}")
                ResultDataBase.Success(value = itemModels)
            }
        }
    }
}
