package by.godevelopment.kingcalculator.domain.gamesdomain.repositories

import by.godevelopment.kingcalculator.domain.commons.models.Players
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel

interface GetMultiItemModelsRepository {

    suspend fun getPlayersByPartyId(partyId: Long): ResultDataBase<Map<Players, PlayerModel>>

    suspend fun getGameNoteById(gameId: Long): ResultDataBase<GameModel>
}
