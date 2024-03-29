package by.godevelopment.kingcalculator.domain.partiesdomain.repositories

import by.godevelopment.kingcalculator.data.entities.PartyNote
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.Players
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel
import by.godevelopment.kingcalculator.domain.partiesdomain.models.RawItemPartyModel
import by.godevelopment.kingcalculator.domain.playersdomain.models.PartyModel
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import kotlinx.coroutines.flow.Flow

interface PartyRepository {

    fun getAllParties(): Flow<List<RawItemPartyModel>>

    suspend fun createNewPartyAndReturnId(party: PartyNote): ResultDataBase<Long>

    suspend fun getAllPlayersIdToNames(): Map<String, Long>

    suspend fun getPlayersByPartyId(partyId: Long): ResultDataBase<Map<Players, PlayerModel>>

    suspend fun getContractorPlayerByPartyId(partyId: Long): ResultDataBase<PlayerModel>

    suspend fun getAllGamesNotesByPartyId(partyId: Long): ResultDataBase<List<GameModel>>

    suspend fun getAllTricksNotesByGameId(gameId: Long): List<TricksNoteModel>

    suspend fun createGameNote(partyId: Long, gameType: GameType): ResultDataBase<Long>

    suspend fun getPartyModelById(partyId: Long): ResultDataBase<PartyModel>

    suspend fun deletePartyById(partyId: Long): ResultDataBase<Int>

    suspend fun getAllPlayersCount(): ResultDataBase<Int>
}
