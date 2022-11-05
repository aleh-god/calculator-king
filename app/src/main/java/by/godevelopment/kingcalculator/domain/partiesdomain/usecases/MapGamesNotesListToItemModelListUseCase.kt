package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import by.godevelopment.kingcalculator.domain.partiesdomain.models.GamesTableItemModel
import javax.inject.Inject

class MapGamesNotesListToItemModelListUseCase @Inject constructor() {

    private val NUMBER_OF_PLAYERS = 4
    private val MOD_PLAYER1 = 0
    private val MOD_PLAYER2 = 1
    private val MOD_PLAYER3 = 2
    private val MOD_PLAYER4 = 3

    operator fun invoke(listGameModel: List<GameModel>): List<GamesTableItemModel> {
        val gamesNotes = listGameModel
            .mapIndexed { index: Int, model -> model to index }

        val openedGamePosition = gamesNotes.size % NUMBER_OF_PLAYERS
        var index = 0

        return GameType
            .values()
            .map { type ->
                val players = gamesNotes
                    .filter { type == it.first.gameType }
                    .map { it.second % NUMBER_OF_PLAYERS }

                GamesTableItemModel(
                    id = index++,
                    gameType = type,
                    isFinishedOneGame = players.contains(MOD_PLAYER1),
                    isFinishedTwoGame = players.contains(MOD_PLAYER2),
                    isFinishedThreeGame = players.contains(MOD_PLAYER3),
                    isFinishedFourGame = players.contains(MOD_PLAYER4),
                    openedColumnIndex = openedGamePosition
                )
            }
    }
}
