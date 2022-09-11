package by.godevelopment.kingcalculator.data.repositories

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.data.datasource.GamesDataSource
import by.godevelopment.kingcalculator.data.datasource.PartiesDataSource
import by.godevelopment.kingcalculator.data.datasource.PlayersDataSource
import by.godevelopment.kingcalculator.data.datasource.TricksDataSource
import by.godevelopment.kingcalculator.data.entities.PartyNote
import by.godevelopment.kingcalculator.data.entities.TricksNote
import by.godevelopment.kingcalculator.data.utils.toPartyModel
import by.godevelopment.kingcalculator.data.utils.toPlayerModel
import by.godevelopment.kingcalculator.data.utils.toTricksNoteModel
import by.godevelopment.kingcalculator.di.IoDispatcher
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.Players
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.commons.utils.mapResult
import by.godevelopment.kingcalculator.domain.commons.utils.wrapResult
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel
import by.godevelopment.kingcalculator.domain.partiesdomain.models.RawItemPartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import by.godevelopment.kingcalculator.domain.playersdomain.models.PartyModel
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import by.godevelopment.kingcalculator.domain.settingsdomain.repositories.DeletePartiesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PartyRepositoryImpl @Inject constructor(
    private val partiesDataSource: PartiesDataSource,
    private val gamesDataSource: GamesDataSource,
    private val tricksDataSource: TricksDataSource,
    private val playersDataSource: PlayersDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : PartyRepository, DeletePartiesRepository {

    override fun getAllParties(): Flow<List<RawItemPartyModel>> {
        val result = partiesDataSource.getAllPartyNotes().map { list ->
            list.map {
                RawItemPartyModel(
                    id = it.id,
                    partyName = it.partyName,
                    startedAt = it.startedAt,
                    partyLastTime = it.partyLastTime,
                    partyGamesCount = gamesDataSource.calculateGamesCountByPartyIdRaw(it.id),
                    player_one = playersDataSource.getActivePlayerProfileByIdRaw(
                        it.playerOneId
                    )?.toPlayerModel(),
                    player_one_tricks = getPlayerTricksByPartyIdRaw(it.id, it.playerOneId),
                    player_two = playersDataSource.getActivePlayerProfileByIdRaw(
                        it.playerTwoId
                    )?.toPlayerModel(),
                    player_two_tricks = getPlayerTricksByPartyIdRaw(it.id, it.playerTwoId),
                    player_three = playersDataSource.getActivePlayerProfileByIdRaw(
                        it.playerThreeId
                    )?.toPlayerModel(),
                    player_three_tricks = getPlayerTricksByPartyIdRaw(it.id, it.playerThreeId),
                    player_four = playersDataSource.getActivePlayerProfileByIdRaw(
                        it.playerFourId
                    )?.toPlayerModel(),
                    player_four_tricks = getPlayerTricksByPartyIdRaw(it.id, it.playerFourId)
                )
            }
        }
        return result
    }

    private suspend fun getPlayerTricksByPartyIdRaw(partyId: Long, playerId: Long): List<TricksNote> =
        withContext(ioDispatcher) {
            val gamesResult = gamesDataSource.getGameNotesByPartyIdRaw(partyId)
            val gamesIdList = gamesResult.map { it.id }
            val tricksResult = mutableListOf<TricksNote>()
            gamesIdList.forEach { gameId ->
                tricksResult.addAll(
                    tricksDataSource.getTricksNoteByGameId(gameId).filter { it.playerId == playerId }
                )
            }
            tricksResult
        }

    override suspend fun createNewPartyAndReturnId(party: PartyNote): ResultDataBase<Long> =
        withContext(ioDispatcher) {
            partiesDataSource.createPartyNote(party)
        }

    override suspend fun getAllPlayersIdToNames(): Map<String, Long> =
        withContext(ioDispatcher) {
            playersDataSource.getAllActivePlayersIdToNames()
        }

    override suspend fun getPlayersByPartyId(partyId: Long): ResultDataBase<Map<Players, PlayerModel>> =
        withContext(ioDispatcher) {
            val partyResult = partiesDataSource.getPartyNoteById(partyId)
            when (partyResult) {
                is ResultDataBase.Success -> {
                    ResultDataBase.Success(
                        value = mapOf<Players, PlayerModel?>(
                            Players.PlayerOne to playersDataSource.getPlayerProfileByIdRaw(
                                partyResult.value.playerOneId
                            )?.toPlayerModel(),
                            Players.PlayerTwo to playersDataSource.getPlayerProfileByIdRaw(
                                partyResult.value.playerTwoId
                            )?.toPlayerModel(),
                            Players.PlayerThree to playersDataSource.getPlayerProfileByIdRaw(
                                partyResult.value.playerThreeId
                            )?.toPlayerModel(),
                            Players.PlayerFour to playersDataSource.getPlayerProfileByIdRaw(
                                partyResult.value.playerFourId
                            )?.toPlayerModel()
                        )
                            .mapValues {
                                if (it.value == null) return@withContext ResultDataBase.Error<Map<Players, PlayerModel>>(message = R.string.message_error_bad_database)
                                it.value!!
                            }
                    )
                }
                is ResultDataBase.Error -> ResultDataBase.Error(
                    message = R.string.message_error_bad_database
                )
            }
        }

    override suspend fun getContractorPlayerByPartyId(partyId: Long): ResultDataBase<PlayerModel> =
        withContext(ioDispatcher) {
            val partyResult = partiesDataSource.getPartyNoteById(partyId)
            when (partyResult) {
                is ResultDataBase.Error -> ResultDataBase.Error(message = partyResult.message)
                is ResultDataBase.Success -> {
                    val party = partyResult.value
                    val gamesCount = gamesDataSource.calculateGamesCountByPartyId(partyId)
                    when (gamesCount) {
                        is ResultDataBase.Error -> ResultDataBase.Error(message = gamesCount.message)
                        is ResultDataBase.Success -> {
                            val playerId = when (gamesCount.value % 4) {
                                0 -> party.playerOneId
                                1 -> party.playerTwoId
                                2 -> party.playerThreeId
                                3 -> party.playerFourId
                                else -> return@withContext ResultDataBase.Error(message = R.string.message_error_data_load)
                            }
                            return@withContext playersDataSource.getPlayerProfileById(playerId)
                                .mapResult { it.toPlayerModel() }
                        }
                    }
                }
            }
        }

    override suspend fun getAllGamesNotesByPartyId(partyId: Long): ResultDataBase<List<GameModel>> =
        withContext(ioDispatcher) {
            val notesListResult = gamesDataSource.getGameNotesByPartyId(partyId)
            when (notesListResult) {
                is ResultDataBase.Error -> ResultDataBase.Error(message = notesListResult.message)
                is ResultDataBase.Success -> {
                    ResultDataBase.Success(
                        value = notesListResult.value.map {
                            GameModel(
                                id = it.id,
                                partyId = it.partyId,
                                gameType = it.gameType,
                                finishedAt = it.finishedAt
                            )
                        }
                    )
                }
            }
        }

    override suspend fun getAllTricksNotesByGameId(gameId: Long): List<TricksNoteModel> =
        withContext(ioDispatcher) {
            tricksDataSource.getTricksNoteByGameId(gameId).map { it.toTricksNoteModel() }
        }

    override suspend fun createGameNote(partyId: Long, gameType: GameType): ResultDataBase<Long> =
        withContext(ioDispatcher) { gamesDataSource.createGameNote(gameType, partyId) }

    override suspend fun getPartyModelById(partyId: Long): ResultDataBase<PartyModel> =
        withContext(ioDispatcher) {
            partiesDataSource.getPartyNoteById(partyId).mapResult { it.toPartyModel() }
        }

    override suspend fun deletePartyById(partyId: Long): ResultDataBase<Int> =
        withContext(ioDispatcher) { partiesDataSource.deletePartyNotesById(partyId) }

    override suspend fun getAllPlayersCount(): ResultDataBase<Int> =
        withContext(ioDispatcher) {
            wrapResult { playersDataSource.getAllActivePlayersNames().size }
        }

    override suspend fun deleteAllParties(): ResultDataBase<Int> =
        withContext(ioDispatcher) { wrapResult { partiesDataSource.deleteAllPartyNotes() } }
}
