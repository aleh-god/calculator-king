package by.godevelopment.kingcalculator.data.datasource

import android.util.Log
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.data.database.PartiesDao
import by.godevelopment.kingcalculator.data.database.PlayersDao
import by.godevelopment.kingcalculator.data.entities.PartyNote
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.Players
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class PartiesDataSource @Inject constructor(
    private val partiesDao: PartiesDao,
    private val playersDao: PlayersDao
) {
    suspend fun getMapPlayersIdByPartyId(partyId: Long): ResultDataBase<Map<Players, PlayerProfile>> {
        partiesDao.getPartyNoteById(partyId)?.let { it ->
            val result = mapOf<Players, PlayerProfile?>(
                    Players.PlayerOne to playersDao.getPlayerProfileById(it.playerOneId),
                    Players.PlayerTwo to playersDao.getPlayerProfileById(it.playerTwoId),
                    Players.PlayerThree to playersDao.getPlayerProfileById(it.playerThreeId),
                    Players.PlayerFour to playersDao.getPlayerProfileById(it.playerFourId),
            )
                .map {
                    if (it.value == null) ResultDataBase.Error<Map<Players, PlayerProfile>>(
                        message = R.string.message_error_bad_database
                    )
                    it.key to it.value!!
                }
                .associate { it }
            if (result.size == 4) return ResultDataBase.Success(value = result)
        }
        return ResultDataBase.Error(message = R.string.message_error_bad_database)
    }

    fun getAllPartyNotes(): Flow<List<PartyNote>> = partiesDao.getAllPartyNotes()

    suspend fun getPartyNoteById(partyId: Long): ResultDataBase<PartyNote> {
        val result = partiesDao.getPartyNoteById(partyId)
        return if (result != null) ResultDataBase.Success(value = result)
        else ResultDataBase.Error(message = R.string.message_error_bad_database)
    }

    suspend fun insertPartyNote(party: PartyNote): Long {
        return partiesDao.insertPartyNote(party)
    }

    suspend fun updateTimeInPartyNoteByPartyId(partyId: Long): ResultDataBase<Int> {
        partiesDao.getPartyNoteById(partyId)?.let {
            val resultUpdate = partiesDao.updatePartyNote(it.copy(
                partyEndTime = Calendar.getInstance().timeInMillis
            ))
            Log.i(TAG, "updateTimeInPartyNoteByPartyId: partyId = $partyId, result = $resultUpdate")
            return ResultDataBase.Success(value = resultUpdate)
        }
        return ResultDataBase.Error<Int>(message = R.string.message_error_data_save)
    }
}
