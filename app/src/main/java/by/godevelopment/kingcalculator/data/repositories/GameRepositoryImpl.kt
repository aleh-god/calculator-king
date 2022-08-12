package by.godevelopment.kingcalculator.data.repositories

import android.util.Log
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.data.datasource.GamesDataSource
import by.godevelopment.kingcalculator.data.datasource.PartiesDataSource
import by.godevelopment.kingcalculator.data.datasource.PlayersDataSource
import by.godevelopment.kingcalculator.data.datasource.TricksDataSource
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.data.utils.toGameNote
import by.godevelopment.kingcalculator.data.utils.toPlayerModel
import by.godevelopment.kingcalculator.data.utils.toTricksNote
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.commons.models.mapResult
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.Players
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GameRepository
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gamesDataSource: GamesDataSource,
    private val partiesDataSource: PartiesDataSource,
    private val tricksDataSource: TricksDataSource,
    private val playersDataSource: PlayersDataSource
): GameRepository {

    override suspend fun getPlayersByPartyId(partyId: Long): ResultDataBase<Map<Players, PlayerModel>> {
        val partyResult = partiesDataSource.getPartyNoteById(partyId)
        return when(partyResult) {
            is ResultDataBase.Success -> {
                ResultDataBase.Success(value = mapOf<Players, PlayerModel?>(
                    Players.PlayerOne to playersDataSource.getPlayerProfileByIdRaw(partyResult.value.playerOneId)?.toPlayerModel(),
                    Players.PlayerTwo to playersDataSource.getPlayerProfileByIdRaw(partyResult.value.playerTwoId)?.toPlayerModel(),
                    Players.PlayerThree to playersDataSource.getPlayerProfileByIdRaw(partyResult.value.playerThreeId)?.toPlayerModel(),
                    Players.PlayerFour to playersDataSource.getPlayerProfileByIdRaw(partyResult.value.playerFourId)?.toPlayerModel(),
                )
                    .mapValues {
                        if (it.value == null) return ResultDataBase.Error<Map<Players, PlayerModel>>(message = R.string.message_error_bad_database)
                        it.value!!
                    }
                )
            }
            is ResultDataBase.Error -> ResultDataBase.Error(message = R.string.message_error_bad_database)
        }
    }

    override suspend fun createGameNote(gameType: GameType, partyId: Long): ResultDataBase<Long> {
        val gameId = gamesDataSource.createGameNote(gameType, partyId)
        Log.i(TAG, "GameRepositoryImpl createGameNote: $gameType , $partyId = $gameId")
        return gameId
    }

    override suspend fun createTricksNote(tricksNoteModel: TricksNoteModel): ResultDataBase<Long> {
        val noteId = tricksDataSource.createTricksNote(tricksNoteModel.toTricksNote())
        Log.i(TAG, "GameRepositoryImpl createTricksNote: $tricksNoteModel = $noteId")
        return noteId
    }

    override suspend fun getPartyIdByGameId(gameId: Long): ResultDataBase<Long> {
        Log.i(TAG, "GameRepositoryImpl getPartyIdByGameId: $gameId")
        return gamesDataSource.getPartyIdByGameId(gameId)
    }

    override suspend fun getGameNoteById(gameId: Long): ResultDataBase<GameModel> {
        Log.i(TAG, "GameRepositoryImpl getGameNoteById: $gameId")
        return gamesDataSource.getGameNoteById(gameId).mapResult { it.toGameNote() }
    }

    private suspend fun updateTimeInPartyNoteByPartyId(gameId: Long): ResultDataBase<Int> {
        val partyIdResult = gamesDataSource.getPartyIdByGameId(gameId)
        return when (partyIdResult) {
            is ResultDataBase.Error -> ResultDataBase.Error(message = partyIdResult.message)
            is ResultDataBase.Success -> {
                Log.i(TAG, "updateTimeInPartyNoteByPartyId: partyIdResult = $partyIdResult")
                partiesDataSource.updateTimeInPartyNoteByPartyId(partyIdResult.value)
            }
        }
    }

    override suspend fun updatePartyStateByGameId(gameId: Long): ResultDataBase<Int> {
        val updateResult = gamesDataSource.updateTimeInGameNoteByGameId(gameId)
        return when (updateResult) {
            is ResultDataBase.Error -> ResultDataBase.Error(message = updateResult.message)
            is ResultDataBase.Success -> {
                Log.i(TAG, "updatePartyStateByGameId: ResultDataBase.Success gameId = $gameId updateResult.value = ${updateResult.value}")
                updateTimeInPartyNoteByPartyId(gameId)
            }
        }
    }
}
