package by.godevelopment.kingcalculator.data.datasource

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.data.database.GamesDao
import by.godevelopment.kingcalculator.data.entities.GameNote
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.commons.utils.wrapResultBy
import java.util.*
import javax.inject.Inject

class GamesDataSource @Inject constructor(
    private val gamesDao: GamesDao
) {

    suspend fun getPartyIdByGameId(gameId: Long): ResultDataBase<Long> {
        return wrapResultBy(gameId) { gamesDao.getGameNoteById(it)?.partyId }
    }

    suspend fun getGameNoteById(gameId: Long): ResultDataBase<GameNote> {
        return wrapResultBy(gameId) { gamesDao.getGameNoteById(it) }
    }

    suspend fun createGameNote(gameType: GameType, partyId: Long): ResultDataBase<Long> {
        val gameId = gamesDao.insertPartyNote(
            GameNote(
                partyId = partyId,
                gameType = gameType)
        )
        return if (gameId != -1L) { ResultDataBase.Success(value = gameId) }
        else ResultDataBase.Error(message = R.string.message_error_bad_database)
    }

    suspend fun calculateGamesCountByPartyIdRaw(partyId: Long): Int =
        gamesDao.getGameNotesByPartyId(partyId).size

    suspend fun updateTimeInGameNoteByGameId(gameId: Long): ResultDataBase<Int> {
        gamesDao.getGameNoteById(gameId)?.let {
            val resultUpdate = gamesDao.updatePartyNote(it.copy(
                finishedAt = Calendar.getInstance().timeInMillis
            ))
            return ResultDataBase.Success(value = resultUpdate)
        }
        return ResultDataBase.Error(message = R.string.message_error_bad_database)
    }

    suspend fun deleteAllGameNotes() = gamesDao.deleteAll()

    // PartyRepositoryImpl

    suspend fun getGameNotesByPartyIdRaw(partyId: Long) =
        gamesDao.getGameNotesByPartyId(partyId)

    suspend fun calculateGamesCountByPartyId(partyId: Long): ResultDataBase<Int> {
        return wrapResultBy(partyId) { gamesDao.getGameNotesByPartyId(it).size }
    }

    suspend fun getGameNotesByPartyId(partyId: Long): ResultDataBase<List<GameNote>> {
        return wrapResultBy(partyId) { gamesDao.getGameNotesByPartyId(it) }
    }

    suspend fun getLastGameByPartyIdRaw(partyId: Long): GameNote? =
        gamesDao.getGameNotesByPartyId(partyId).maxByOrNull { it.finishedAt }
}
