package by.godevelopment.kingcalculator.domain.gamesdomain.repositories

import by.godevelopment.kingcalculator.data.entities.GameNote
import by.godevelopment.kingcalculator.data.models.PlayersInGameModel
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel

interface GameRepository {

    suspend fun getPlayersByPartyId(partyId: Long): ResultDataBase<PlayersInGameModel>

    suspend fun createGameNote(gameType: GameType, partyId: Long): ResultDataBase<Long>

    suspend fun createTricksNote(tricksNoteModel: TricksNoteModel): ResultDataBase<Long>

    suspend fun getPartyIdByGameId(gameId: Long): ResultDataBase<Long>

    suspend fun getGameNoteById(gameId: Long): ResultDataBase<GameNote>
}