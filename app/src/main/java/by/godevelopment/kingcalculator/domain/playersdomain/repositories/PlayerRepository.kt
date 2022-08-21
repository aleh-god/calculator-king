package by.godevelopment.kingcalculator.domain.playersdomain.repositories

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel
import by.godevelopment.kingcalculator.domain.playersdomain.models.PartyModel
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel

import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    suspend fun getPlayerById(id: Long): ResultDataBase<PlayerModel>

    suspend fun createPlayer(params: PlayerModel): ResultDataBase<Long>

    suspend fun updatePlayerById(params: PlayerModel): ResultDataBase<Int>

    suspend fun disablePlayerById(params: PlayerModel): ResultDataBase<Int>

    suspend fun getAllPlayersNames(): List<String>

    fun getAllPlayers(): Flow<List<PlayerModel>>

    suspend fun getAllPartiesByPlayerId(playerId: Long): ResultDataBase<List<PartyModel>>

    suspend fun getAllTricksByPlayerId(playerId: Long): ResultDataBase<List<TricksNoteModel>>

    suspend fun getAllGamesByPartyId(parties: List<Long>): ResultDataBase<List<GameModel>>
}
