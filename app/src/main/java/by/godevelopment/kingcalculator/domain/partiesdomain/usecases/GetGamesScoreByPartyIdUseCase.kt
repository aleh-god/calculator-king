package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.partiesdomain.models.PartyInfoItemModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import javax.inject.Inject

class GetGamesScoreByPartyIdUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {

    suspend operator fun invoke(partyId: Long): ResultDataBase<List<PartyInfoItemModel>> {
        val partyResult = partyRepository.getPartyModelById(partyId)
        return when (partyResult) {
            is ResultDataBase.Error -> ResultDataBase.Error<List<PartyInfoItemModel>>(message = partyResult.message)
            is ResultDataBase.Success -> {
                val party = partyResult.value
                val gamesResult = partyRepository.getAllGamesNotesByPartyId(partyId)
                when (gamesResult) {
                    is ResultDataBase.Error -> ResultDataBase.Error<List<PartyInfoItemModel>>(message = gamesResult.message)
                    is ResultDataBase.Success -> {
                        val scoresList = gamesResult
                            .value
                            .sortedBy { it.id }
                            .mapIndexed { index, game ->
                                val tricksResult = partyRepository.getAllTricksNotesByGameId(game.id)
                                PartyInfoItemModel(
                                    id = index,
                                    gameType = game.gameType,
                                    oneGameScore = tricksResult
                                        .filter { it.playerId == party.playerOneId }
                                        .sumOf { it.gameType.getGameScore(it.tricksCount) },
                                    twoGameScore = tricksResult
                                        .filter { it.playerId == party.playerTwoId }
                                        .sumOf { it.gameType.getGameScore(it.tricksCount) },
                                    threeGameScore = tricksResult
                                        .filter { it.playerId == party.playerThreeId }
                                        .sumOf { it.gameType.getGameScore(it.tricksCount) },
                                    fourGameScore = tricksResult
                                        .filter { it.playerId == party.playerFourId }
                                        .sumOf { it.gameType.getGameScore(it.tricksCount) }
                                )
                            }
                        ResultDataBase.Success(value = scoresList)
                    }
                }
            }
        }
    }
}
