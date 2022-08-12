package by.godevelopment.kingcalculator.data.datasource

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.data.database.TricksDao
import by.godevelopment.kingcalculator.data.entities.TricksNote
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import javax.inject.Inject

class TricksDataSource @Inject constructor(
    private val tricksDao: TricksDao
) {

    suspend fun createTricksNote(tricksNote: TricksNote): ResultDataBase<Long> {
        val noteId = tricksDao.insertTricksNote(tricksNote)
        return if (noteId != -1L) { ResultDataBase.Success(value = noteId) }
        else ResultDataBase.Error(message = R.string.message_error_bad_database)
    }

    suspend fun getTricksNoteByGameId(gameId: Long): List<TricksNote>
            = tricksDao.getTricksNotesByGameId(gameId)
}
