package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.BODY_ROW_TYPE
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.SaveGameRepository
import javax.inject.Inject

class SaveGameUseCase @Inject constructor(
    private val saveGameRepository: SaveGameRepository
) {

    suspend operator fun invoke(gameId: Long, items: List<MultiItemModel>): ResultDataBase<Boolean> {
        return if (gameId > 0) {
            val notesResult = items
                .filter { it.itemViewType == BODY_ROW_TYPE }
                .map {
                    saveGameRepository.createTricksNote(
                        TricksNoteModel(
                            gameId = gameId,
                            playerId = it.player.id,
                            gameType = it.gameType,
                            tricksCount = it.tricks
                        )
                    )
                }
            if (notesResult.any { it is ResultDataBase.Success }) {
                return when (saveGameRepository.updatePartyStateByGameId(gameId)) {
                    is ResultDataBase.Error -> {
                        undoBadDbTransaction(gameId)
                        ResultDataBase.Error(message = R.string.message_error_data_save)
                    }
                    is ResultDataBase.Success -> ResultDataBase.Success(true)
                }
            } else {
                val undoResult = undoBadDbTransaction(gameId)
                when (undoResult) {
                    is ResultDataBase.Error -> ResultDataBase.Error(message = undoResult.message)
                    is ResultDataBase.Success -> ResultDataBase.Error(message = R.string.message_error_data_save)
                }
            }
        } else {
            ResultDataBase.Error(message = R.string.message_error_data_unknown)
        }
    }

    private suspend fun undoBadDbTransaction(gameId: Long): ResultDataBase<Int> {
        return saveGameRepository.undoBadDbTransaction(gameId)
    }
}
