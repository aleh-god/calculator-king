package by.godevelopment.kingcalculator.data.repositories

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.data.datasource.GamesDataSource
import by.godevelopment.kingcalculator.data.datasource.PartiesDataSource
import by.godevelopment.kingcalculator.data.datasource.PlayersDataSource
import by.godevelopment.kingcalculator.data.datasource.TricksDataSource
import by.godevelopment.kingcalculator.data.utils.*
import by.godevelopment.kingcalculator.di.IoDispatcher
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.commons.utils.mapResult
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel
import by.godevelopment.kingcalculator.domain.playersdomain.models.PartyModel
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import by.godevelopment.kingcalculator.domain.playersdomain.repositories.PlayerCardRepository
import by.godevelopment.kingcalculator.domain.playersdomain.repositories.PlayerRepository
import by.godevelopment.kingcalculator.domain.settingsdomain.repositories.DeletePlayersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playersDataSource: PlayersDataSource,
    private val partiesDataSource: PartiesDataSource,
    private val gamesDataSource: GamesDataSource,
    private val tricksDataSource: TricksDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : PlayerRepository, DeletePlayersRepository, PlayerCardRepository {

    override suspend fun getPlayerById(id: Long): ResultDataBase<PlayerModel> =
        withContext(ioDispatcher) {
            playersDataSource.getPlayerProfileById(id).mapResult { it.toPlayerModel() }
        }

    override suspend fun createPlayer(params: PlayerModel): ResultDataBase<Long> =
        withContext(ioDispatcher) {
            playersDataSource.createPlayer(params.toPlayerProfile())
        }

    override suspend fun updatePlayerById(params: PlayerModel): ResultDataBase<Int> =
        withContext(ioDispatcher) {
            playersDataSource.updatePlayerById(params.toPlayerProfile())
        }

    override suspend fun disablePlayerById(params: PlayerModel): ResultDataBase<Int> =
        withContext(ioDispatcher) {
            val parties = partiesDataSource.getPartyNoteByPlayerId(params.id)
            when (parties) {
                is ResultDataBase.Error -> Unit
                is ResultDataBase.Success -> {
                    parties.value.forEach {
                        partiesDataSource.updateTimeInPartyNoteByPartyId(it.id)
                    }
                }
            }
            playersDataSource.disablePlayerById(params.toPlayerProfile())
        }

    override suspend fun deleteAllPlayers(): ResultDataBase<Int> =
        withContext(ioDispatcher) {
            playersDataSource.deleteAllPlayers()
        }

    override suspend fun getAllPlayersNames(): List<String> =
        withContext(ioDispatcher) {
            playersDataSource.getAllActivePlayersNames()
        }

    override fun getAllPlayers(): Flow<List<PlayerModel>> =
        playersDataSource
            .getAllPlayers()
            .map { list -> list.map { it.toPlayerModel() } }

    override suspend fun getAllPartiesByPlayerId(playerId: Long): ResultDataBase<List<PartyModel>> =
        withContext(ioDispatcher) {
            partiesDataSource.getPartyNoteByPlayerId(playerId).mapResult { list ->
                list.map { it.toPartyModel() }
            }
        }

    override suspend fun getAllTricksByPlayerId(playerId: Long): ResultDataBase<List<TricksNoteModel>> =
        withContext(ioDispatcher) {
            tricksDataSource.getTricksNoteByPlayerId(playerId).mapResult { list ->
                list.map { it.toTricksNoteModel() }
            }
        }

    override suspend fun getAllGamesByPartyId(parties: List<Long>): ResultDataBase<List<GameModel>> =
        withContext(ioDispatcher) {
            val gameModels = parties.map { partyId  ->
                val notesResult = gamesDataSource.getGameNotesByPartyId(partyId)
                when(notesResult) {
                    is ResultDataBase.Error -> emptyList()
                    is ResultDataBase.Success -> notesResult.value.map { it.toGameModel() }
                }
            }.flatten()

            if(gameModels.isEmpty()) ResultDataBase.Error(message = R.string.message_error_data_load)
            else ResultDataBase.Success(value = gameModels)
        }
}
