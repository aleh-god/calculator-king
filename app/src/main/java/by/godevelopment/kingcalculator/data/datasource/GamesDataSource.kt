package by.godevelopment.kingcalculator.data.datasource

import by.godevelopment.kingcalculator.data.database.GamesDao
import javax.inject.Inject

class GamesDataSource @Inject constructor(
    private val gamesDao: GamesDao
) {

    suspend fun getGamesCountByPartyId(partyId: Long) = gamesDao.getGameNotesByPartyId(partyId).size
}