package by.godevelopment.kingcalculator.data.repositories

import android.util.Log
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.data.database.GamesDao
import by.godevelopment.kingcalculator.data.database.PartiesDao
import by.godevelopment.kingcalculator.data.database.TricksDao
import by.godevelopment.kingcalculator.data.datasource.PlayersDataSource
import by.godevelopment.kingcalculator.data.entities.PartyNote
import by.godevelopment.kingcalculator.data.entities.TrickNote
import by.godevelopment.kingcalculator.domain.partiesdomain.models.PartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PartyRepositoryImpl @Inject constructor(
//    private val partiesDataSource: PartiesDataSource,
    private val partiesDao: PartiesDao,
    private val gamesDao: GamesDao,
    private val tricksDao: TricksDao,
    private val playersDataSource: PlayersDataSource
) : PartyRepository {

    override fun getAllParties(): Flow<List<PartyModel>> {
        val result = partiesDao.getAllPartyNotes().map { list ->
            Log.i(TAG, "PartyRepositoryImpl getAllParties: ${list.size}")
            list.map {
                PartyModel(
                    id = it.id,
                    partyName = it.partyName,
                    startedAt = it.startedAt,
                    partyGamesCount = calculateGamesCountByPartyId(it.id),
                    player_one_name = getPlayerNameByPlayerId(it.playerOneId),
                    player_one_tricks = getPayerTricksByPartyId(it.id, it.playerOneId),
                    player_two_name = getPlayerNameByPlayerId(it.playerTwoId),
                    player_two_tricks = getPayerTricksByPartyId(it.id, it.playerTwoId),
                    player_three_name = getPlayerNameByPlayerId(it.playerThreeId),
                    player_three_tricks = getPayerTricksByPartyId(it.id, it.playerThreeId),
                    player_four_name = getPlayerNameByPlayerId(it.playerFourId),
                    player_four_tricks = getPayerTricksByPartyId(it.id, it.playerFourId),
                )
            }
        }
        return result
    }

    private suspend fun getPayerTricksByPartyId(partyId: Long, playerId: Long): List<TrickNote> {
        val gameIdList = gamesDao.getGameNotesByPartyId(partyId).map { it.id }
        val tricks = gameIdList.map {
            tricksDao.getTrickNoteById(it)
        }
        Log.i(TAG, "getPayerTricksByPartyId: gameIdList = ${gameIdList.size} tricks = ${tricks.size}")
        return tricks
    }

    private suspend fun getPlayerNameByPlayerId(playerId: Long): String =
        playersDataSource.getPlayerProfileById(playerId).name


    private suspend fun calculateGamesCountByPartyId(partyId: Long): Int {
        return gamesDao.getGameNotesByPartyId(partyId).size
    }

    override suspend fun createNewPartyAndReturnId(party: PartyNote): Long =
        partiesDao.insertPartyNote(party)

    override suspend fun getAllPlayersIdToNames(): Map<String, Long> =
        playersDataSource.getAllPlayersIdToNames()

    override suspend fun getPlayersByPartyId(partyId: Long): List<String> {
        // TODO("to implemented getPlayersByPartyId")
        return listOf(
            "Leonardo",
            "Raphael",
            "Donatello",
            "Michelangelo"
        )
    }

    override suspend fun getContractorPlayerByPartyId(partyId: Long): String {
        // TODO("to implemented getContractorPlayerByPartyId")
        return "Michelangelo"
    }
}
