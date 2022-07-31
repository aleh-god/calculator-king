package by.godevelopment.kingcalculator.domain.partiesdomain.repositories

import by.godevelopment.kingcalculator.data.entities.PartyNote
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import by.godevelopment.kingcalculator.domain.partiesdomain.models.PartyModel
import kotlinx.coroutines.flow.Flow

interface PartyRepository {

    fun getAllParties(): Flow<List<PartyModel>>

    suspend fun createNewPartyAndReturnId(party: PartyNote): Long

    suspend fun getAllPlayersIdToNames(): Map<String, Long>

    suspend fun getPlayersByPartyId(partyId: Long): List<String>

    suspend fun getContractorPlayerByPartyId(partyId: Long): String

    suspend fun getAllGamesNotesByPartyId(partyId: Long): List<GameModel>

    suspend fun createGameNote(partyId: Long, gameType: GameType) : ResultDataBase<Long>
}
