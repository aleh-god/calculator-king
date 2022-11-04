package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.partiesdomain.models.GamesTableItemModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import javax.inject.Inject

class GetGamesByPartyIdUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {

    private val NUMBER_OF_PLAYERS = 4
    private val MOD_PLAYER1 = 0
    private val MOD_PLAYER2 = 1
    private val MOD_PLAYER3 = 2
    private val MOD_PLAYER4 = 3

    suspend operator fun invoke(partyId: Long): ResultDataBase<List<GamesTableItemModel>> {
        val listResult = partyRepository.getAllGamesNotesByPartyId(partyId)
        return when (listResult) {
            is ResultDataBase.Error -> ResultDataBase.Error(message = listResult.message)
            is ResultDataBase.Success -> {

                val gamesNotes = listResult
                    .value
                    .mapIndexed { index: Int, model -> model to index }

                val openedGamePosition = gamesNotes.size % NUMBER_OF_PLAYERS

                val itemModels: List<GamesTableItemModel> = GameType
                    .values()
                    .mapIndexed { index, gameType ->

                        val players = gamesNotes
                            .filter { gameType == it.first.gameType }
                            .map { it.second % NUMBER_OF_PLAYERS }

                        GamesTableItemModel(
                            id = index,
                            gameType = gameType,
                            isFinishedOneGame = players.contains(MOD_PLAYER1),
                            isFinishedTwoGame = players.contains(MOD_PLAYER2),
                            isFinishedThreeGame = players.contains(MOD_PLAYER3),
                            isFinishedFourGame = players.contains(MOD_PLAYER4),
                            openedColumnIndex = openedGamePosition
                        )
                    }
                ResultDataBase.Success(value = itemModels)
            }
        }
    }
}
