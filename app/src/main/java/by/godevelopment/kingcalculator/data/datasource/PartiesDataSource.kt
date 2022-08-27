package by.godevelopment.kingcalculator.data.datasource

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.data.database.PartiesDao
import by.godevelopment.kingcalculator.data.entities.PartyNote
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.commons.utils.wrapResultBy
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class PartiesDataSource @Inject constructor(
    private val partiesDao: PartiesDao
) {

    fun getAllPartyNotes(): Flow<List<PartyNote>> = partiesDao.getAllPartyNotes()

    suspend fun getPartyNoteById(partyId: Long): ResultDataBase<PartyNote> =
        wrapResultBy(partyId) { partiesDao.getPartyNoteById(it) }

    suspend fun createPartyNote(party: PartyNote): ResultDataBase<Long> =
        wrapResultBy(party) { partiesDao.insertPartyNote(it) }

    suspend fun updateTimeInPartyNoteByPartyId(partyId: Long): ResultDataBase<Int> {
        partiesDao.getPartyNoteById(partyId)?.let {
            val resultUpdate = partiesDao.updatePartyNote(it.copy(
                partyLastTime = Calendar.getInstance().timeInMillis
            ))
            return ResultDataBase.Success(value = resultUpdate)
        }
        return ResultDataBase.Error(message = R.string.message_error_data_save)
    }

    suspend fun deleteAllPartyNotes(): Int = partiesDao.deleteAll()

    suspend fun deletePartyNotesById(partyId: Long): ResultDataBase<Int> =
        wrapResultBy(partyId) { partiesDao.deletePartyNotesById(it) }

    suspend fun getPartyNoteByPlayerId(playerId: Long): ResultDataBase<List<PartyNote>> =
        wrapResultBy(playerId) { partiesDao.getAllPartiesListByPlayerId(it) }
}
