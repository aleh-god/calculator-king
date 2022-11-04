package by.godevelopment.kingcalculator.domain.playersdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.EMPTY_STRING
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel
import by.godevelopment.kingcalculator.domain.playersdomain.models.ItemPlayerInfoModel
import javax.inject.Inject

class GetPlayerGamesScoreListUseCase @Inject constructor() {

    operator fun invoke(tricks: List<TricksNoteModel>, games: List<GameModel>): List<ItemPlayerInfoModel> {
        return listOf(
            ItemPlayerInfoModel(
                type = R.string.use_case_score_by_type,
                value = EMPTY_STRING
            )
        ) + games
            .map { game ->
                game to tricks
                    .filter { it.gameId == game.id }
                    .sumOf { it.gameType.getGameScore(it.tricksCount) }
            }
            .groupBy(
                keySelector = { it.first.gameType },
                valueTransform = { it.second }
            )
            .map { entry ->
                val type = entry.key.res
                val value = entry.value.sumOf { it }
                ItemPlayerInfoModel(
                    type = type,
                    value = value.toString()
                )
            }
    }
}
