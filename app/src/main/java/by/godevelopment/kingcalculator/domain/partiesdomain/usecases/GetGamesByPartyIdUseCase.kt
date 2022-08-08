package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.partiesdomain.models.GamesTableItemModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import javax.inject.Inject

class GetGamesByPartyIdUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {

    suspend operator fun invoke(partyId: Long): ResultDataBase<List<GamesTableItemModel>> {
        val listResult = partyRepository.getAllGamesNotesByPartyId(partyId)
        return when(listResult) {
            is ResultDataBase.Error -> ResultDataBase.Error(message = listResult.message)
            is ResultDataBase.Success -> {
                val gamesNotes = listResult
                    .value
                    .sortedBy { it.gameType.id }
                    .mapIndexed { index: Int, model -> model to index }

                val openedGamePosition = gamesNotes.size % 4
                var index: Int = 0
                val itemModels: List<GamesTableItemModel> = GameType
                    .values()
                    .map { type ->

                        val players = gamesNotes
                            .filter { type == it.first.gameType }
                            .map { it.second % 4 }

                        GamesTableItemModel(
                            id = index++,
                            gameType = type,
                            isFinishedOneGame = players.contains(0),
                            isFinishedTwoGame = players.contains(1),
                            isFinishedThreeGame = players.contains(2),
                            isFinishedFourGame = players.contains(3),
                            openedColumnIndex = openedGamePosition
                        )
                    }
                ResultDataBase.Success(value = itemModels)
            }
        }
    }
}
