package by.godevelopment.kingcalculator.domain.gamesdomain.repositories

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel

interface SaveGameRepository {

    suspend fun createTricksNote(tricksNoteModel: TricksNoteModel): ResultDataBase<Long>

    suspend fun updatePartyStateByGameId(gameId: Long): ResultDataBase<Int>

    suspend fun undoBadDbTransaction(gameId: Long): ResultDataBase<Int>
}
