package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import android.util.Log
import by.godevelopment.kingcalculator.commons.BODY_ROW_TYPE
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GameRepository
import javax.inject.Inject

class SaveGameUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(gameId: Long, items: List<MultiItemModel>): Boolean {
        Log.i(TAG, "SaveGameUseCase invoke: $gameId = ${items.size}")
        return if (gameId > 0) {
            val notesResult = items
                .filter { it.itemViewType == BODY_ROW_TYPE }
                .map {
                    gameRepository.createTricksNote(
                        TricksNoteModel(
                            gameId = gameId,
                            playerId = it.player.id,
                            gameType = it.gameType,
                            tricksCount = it.tricks
                        )
                    )
                }
            Log.i(TAG, "SaveGameUseCase: ${notesResult.size}")
            notesResult.any { it is ResultDataBase.Success } // TODO("Clear bad transaction in DB")
        }
        else false
    }
}