package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import android.util.Log
import by.godevelopment.kingcalculator.commons.BODY_ROW_TYPE
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GameRepository
import javax.inject.Inject

class SaveGameUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend fun invoke(gameId: Long, items: List<MultiItemModel>) {
        Log.i(TAG, "SaveGameUseCase invoke: ${items.size}")
        val result = items.filter { it.itemViewType == BODY_ROW_TYPE }.map {
            val result = gameRepository.saveTricksNote(
                TricksNoteModel(
                    gameId = gameId,
                    playerId = it.player.id,
                    gameType = it.gameType.name,
                    tricksCount = it.tricks
                )
            )
            it.rowId to result
        }
    }
}