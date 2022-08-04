package by.godevelopment.kingcalculator.data.datasource

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.data.database.PartiesDao
import by.godevelopment.kingcalculator.data.database.PlayersDao
import by.godevelopment.kingcalculator.data.entities.PartyNote
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.Players
import kotlinx.coroutines.flow.Flow
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
}
