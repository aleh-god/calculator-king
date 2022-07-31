package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.partiesdomain.models.GamesTableItemModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGamesByPartyIdUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {

    suspend operator fun invoke(partyId: Long): Flow<List<GamesTableItemModel>> {

        val gamesNotes = partyRepository
            .getAllGamesNotesByPartyId(partyId)
            .sortedBy { it.gameType.id }
            .mapIndexed { index: Int, model -> model to index }

        val openedGamePosition = gamesNotes.size % 4
        var index: Int = 0
        val result: List<GamesTableItemModel> = GameType
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
        return flow { emit(result) }
    }
}