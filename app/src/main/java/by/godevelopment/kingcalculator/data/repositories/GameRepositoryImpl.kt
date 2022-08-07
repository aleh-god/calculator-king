package by.godevelopment.kingcalculator.data.repositories

import android.util.Log
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.data.datasource.*
import by.godevelopment.kingcalculator.data.entities.GameNote
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.Players
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val tricksDataSource: TricksDataSource,
    private val gamesDataSource: GamesDataSource,
    private val partiesDataSource: PartiesDataSource
): GameRepository {

    override suspend fun getPlayersByPartyId(partyId: Long):
            ResultDataBase<Map<Players, PlayerProfile>> =
        partiesDataSource.getMapPlayersIdByPartyId(partyId)

    override suspend fun createGameNote(gameType: GameType, partyId: Long): ResultDataBase<Long> {
        val gameId = gamesDataSource.createGameNote(gameType, partyId)
        Log.i(TAG, "GameRepositoryImpl createGameNote: $gameType , $partyId = $gameId")
        return gameId
    }

    override suspend fun createTricksNote(tricksNoteModel: TricksNoteModel): ResultDataBase<Long> {
        val noteId = tricksDataSource.createTricksNote(tricksNoteModel)
        Log.i(TAG, "GameRepositoryImpl createTricksNote: $tricksNoteModel = $noteId")
        return noteId
    }

    override suspend fun getPartyIdByGameId(gameId: Long): ResultDataBase<Long> {
        Log.i(TAG, "GameRepositoryImpl getPartyIdByGameId: $gameId")
        return gamesDataSource.getPartyIdByGameId(gameId)
    }

    override suspend fun getGameNoteById(gameId: Long): ResultDataBase<GameNote> {
        Log.i(TAG, "GameRepositoryImpl getGameNoteById: $gameId")
        return gamesDataSource.getGameNoteById(gameId)
    }

    override suspend fun updatePartyStateByGameId(gameId: Long): ResultDataBase<Int>  {
        gamesDataSource.updateTimeInGameNoteByGameId(gameId)
        val partyId = gamesDataSource.getPartyIdByGameId(gameId)
        return when(partyId) {
            is ResultDataBase.Error -> { ResultDataBase.Error(message = partyId.message) }
            is ResultDataBase.Success -> {
                Log.i(TAG, "updatePartyStateByGameId: ResultDataBase.Success gameId = $gameId partyId.value = ${partyId.value}")
                partiesDataSource.updateTimeInPartyNoteByPartyId(partyId.value)
            }
        }
    }
}
