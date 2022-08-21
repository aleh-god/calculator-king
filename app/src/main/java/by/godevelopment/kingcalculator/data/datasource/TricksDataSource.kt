package by.godevelopment.kingcalculator.data.datasource

import android.util.Log
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.ROWS_NOT_INSERTED
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.data.database.TricksDao
import by.godevelopment.kingcalculator.data.entities.TricksNote
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.commons.utils.wrapResultBy
import javax.inject.Inject

class TricksDataSource @Inject constructor(
    private val tricksDao: TricksDao
) {

    suspend fun createTricksNote(tricksNote: TricksNote): ResultDataBase<Long> {
        val noteId = tricksDao.insertTricksNote(tricksNote)
        return if (noteId != ROWS_NOT_INSERTED) { ResultDataBase.Success(value = noteId) }
        else ResultDataBase.Error(message = R.string.message_error_bad_database)
    }

    suspend fun getTricksNoteByGameId(gameId: Long): List<TricksNote> =
        tricksDao.getTricksNotesByGameId(gameId)

    suspend fun deleteTricksNotesByGameId(gameId: Long): ResultDataBase<Int> {
        return try {
            ResultDataBase.Success(
                value = tricksDao.getTricksNotesByGameId(gameId)
                    .onEach { tricksDao.deleteTricksNote(it) }
                    .size
            )
        } catch (e: Exception) {
            Log.i(TAG, "deleteTricksNotesByGameId: ${e.message}")
            ResultDataBase.Error(message = R.string.message_error_bad_database)
        }
    }

    suspend fun getTricksNoteByPlayerId(playerId: Long): ResultDataBase<List<TricksNote>> =
        wrapResultBy(playerId) { tricksDao.getTricksNotesByPlayerId(it) }
}
