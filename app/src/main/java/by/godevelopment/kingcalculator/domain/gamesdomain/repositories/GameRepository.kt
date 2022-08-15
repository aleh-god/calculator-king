package by.godevelopment.kingcalculator.domain.gamesdomain.repositories

import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.Players
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel

interface GameRepository {

    suspend fun getPlayersByPartyId(partyId: Long): ResultDataBase<Map<Players, PlayerModel>>

    suspend fun createGameNote(gameType: GameType, partyId: Long): ResultDataBase<Long>

    suspend fun createTricksNote(tricksNoteModel: TricksNoteModel): ResultDataBase<Long>

    suspend fun getPartyIdByGameId(gameId: Long): ResultDataBase<Long>

    suspend fun getGameNoteById(gameId: Long): ResultDataBase<GameModel>

    suspend fun updatePartyStateByGameId(gameId: Long): ResultDataBase<Int>

    suspend fun undoBadDbTransaction(gameId: Long): ResultDataBase<Int>
}