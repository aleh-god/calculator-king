package by.godevelopment.kingcalculator.data.repositories

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.data.datasource.GamesDataSource
import by.godevelopment.kingcalculator.data.datasource.PartiesDataSource
import by.godevelopment.kingcalculator.data.datasource.PlayersDataSource
import by.godevelopment.kingcalculator.data.datasource.TricksDataSource
import by.godevelopment.kingcalculator.data.utils.toGameModel
import by.godevelopment.kingcalculator.data.utils.toPlayerModel
import by.godevelopment.kingcalculator.data.utils.toTricksNote
import by.godevelopment.kingcalculator.di.IoDispatcher
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.commons.utils.flatMapResult
import by.godevelopment.kingcalculator.domain.commons.utils.mapResult
import by.godevelopment.kingcalculator.domain.commons.utils.wrapResult
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.Players
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GetMultiItemModelsRepository
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.SaveGameRepository
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GetPartyIdByGameIdRepository
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import by.godevelopment.kingcalculator.domain.settingsdomain.repositories.DeleteGamesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gamesDataSource: GamesDataSource,
    private val partiesDataSource: PartiesDataSource,
    private val tricksDataSource: TricksDataSource,
    private val playersDataSource: PlayersDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
):
    DeleteGamesRepository,
    GetPartyIdByGameIdRepository,
    SaveGameRepository,
    GetMultiItemModelsRepository
{

    override suspend fun getPlayersByPartyId(partyId: Long): ResultDataBase<Map<Players, PlayerModel>> =
        withContext(ioDispatcher) {
        val partyResult = partiesDataSource.getPartyNoteById(partyId)
        when(partyResult) {
            is ResultDataBase.Success -> {
                ResultDataBase.Success(value = mapOf<Players, PlayerModel?>(
                    Players.PlayerOne to playersDataSource.getPlayerProfileByIdRaw(partyResult.value.playerOneId)?.toPlayerModel(),
                    Players.PlayerTwo to playersDataSource.getPlayerProfileByIdRaw(partyResult.value.playerTwoId)?.toPlayerModel(),
                    Players.PlayerThree to playersDataSource.getPlayerProfileByIdRaw(partyResult.value.playerThreeId)?.toPlayerModel(),
                    Players.PlayerFour to playersDataSource.getPlayerProfileByIdRaw(partyResult.value.playerFourId)?.toPlayerModel(),
                )
                    .mapValues {
                        if (it.value == null) return@withContext ResultDataBase.Error<Map<Players, PlayerModel>>(message = R.string.message_error_bad_database)
                        it.value!!
                    }
                )
            }
            is ResultDataBase.Error -> ResultDataBase.Error(message = partyResult.message)
        }
    }

    override suspend fun createTricksNote(tricksNoteModel: TricksNoteModel): ResultDataBase<Long> =
        withContext(ioDispatcher) {
            tricksDataSource.createTricksNote(tricksNoteModel.toTricksNote())
        }

    override suspend fun getPartyIdByGameId(gameId: Long): ResultDataBase<Long> =
        withContext(ioDispatcher) { gamesDataSource.getPartyIdByGameId(gameId) }

    override suspend fun getGameNoteById(gameId: Long): ResultDataBase<GameModel> =
        withContext(ioDispatcher) {
            gamesDataSource.getGameNoteById(gameId).mapResult { it.toGameModel() }
        }

    private suspend fun updateTimeInPartyNoteByPartyId(gameId: Long): ResultDataBase<Int> =
        withContext(ioDispatcher) {
            gamesDataSource.getPartyIdByGameId(gameId).flatMapResult {
                partiesDataSource.updateTimeInPartyNoteByPartyId(it)
            }
        }

    override suspend fun updatePartyStateByGameId(gameId: Long): ResultDataBase<Int> =
        withContext(ioDispatcher) {
            gamesDataSource.updateTimeInGameNoteByGameId(gameId).flatMapResult {
                updateTimeInPartyNoteByPartyId(gameId)
            }
        }

    override suspend fun undoBadDbTransaction(gameId: Long): ResultDataBase<Int> =
        withContext(ioDispatcher) { tricksDataSource.deleteTricksNotesByGameId(gameId) }

    override suspend fun deleteAllGames(): ResultDataBase<Int> =
        withContext(ioDispatcher) {
            wrapResult { gamesDataSource.deleteAllGameNotes() }
        }
}
