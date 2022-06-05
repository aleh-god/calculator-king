package by.godevelopment.kingcalculator.data.repositories

import android.util.Log
import by.godevelopment.kingcalculator.commons.DELETED_STRING_VALUE
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.data.database.GamesDao
import by.godevelopment.kingcalculator.data.database.PartiesDao
import by.godevelopment.kingcalculator.data.database.PlayersDao
import by.godevelopment.kingcalculator.data.datasource.PlayerDataSource
import by.godevelopment.kingcalculator.data.entities.PartyNote
import by.godevelopment.kingcalculator.domain.partiesdomain.models.PartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PartyRepositoryImpl @Inject constructor(
    private val partyDao: PartiesDao,
    private val gamesDao: GamesDao,
    private val playersDataSource: PlayerDataSource
) : PartyRepository {

    override fun getAllPartyModels(): Flow<List<PartyModel>> {
        val result = partyDao.getAllPartyNotes().map { list ->
            list.map {
                PartyModel(
                    id = it.id,
                    partyName = it.partyName,
                    startedAt = it.startedAt,
                    partyGamesCount = calculateGamesCountByPartyId(it.id),
                    player_one_name = getPlayerNameByPlayerId(it.playerOneId),
                    player_one_score = calculatePayerScoreByPartyId(it.id, it.playerOneId),
                    player_two_name = getPlayerNameByPlayerId(it.playerTwoId),
                    player_two_score = calculatePayerScoreByPartyId(it.id, it.playerTwoId),
                    player_three_name = getPlayerNameByPlayerId(it.playerThreeId),
                    player_three_score = calculatePayerScoreByPartyId(it.id, it.playerThreeId),
                    player_four_name = getPlayerNameByPlayerId(it.playerFourId),
                    player_four_score = calculatePayerScoreByPartyId(it.id, it.playerFourId),
                )
            }
        }
        Log.i(TAG, "PartyRepositoryImpl getAllPartyModels: ")
        return result
    }

    private suspend fun calculatePayerScoreByPartyId(partyId: Long, playerId: Long): Int {
        val result = gamesDao.getAllGameNotes()
        TODO("Not yet implemented")
        return -1
    }

    private suspend fun getPlayerNameByPlayerId(playerId: Long): String =
        playersDataSource.getPlayerProfileById(playerId).name ?: DELETED_STRING_VALUE


    private suspend fun calculateGamesCountByPartyId(partyId: Long): Int {
        TODO("Not yet implemented")
        return -1
    }

    override suspend fun createNewPartyAndReturnId(party: PartyNote): Long {
        Log.i(TAG, "PartyRepositoryImpl createNewPartyAndReturnId: $party")
        val createResult = partyDao.insertPartyNote(party)
        Log.i(TAG, "PartyRepositoryImpl createNewPartyAndReturnId createResult: $createResult")
        return createResult
    }

    override suspend fun getAllPlayersIdToNames(): Map<String, Long> {
        return playersDataSource.getAllPlayersIdToNames()
    }
}