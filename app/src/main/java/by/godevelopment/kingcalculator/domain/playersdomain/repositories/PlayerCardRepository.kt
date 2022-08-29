package by.godevelopment.kingcalculator.domain.playersdomain.repositories

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel

interface PlayerCardRepository {

    suspend fun updatePlayerById(params: PlayerModel): ResultDataBase<Int>

    suspend fun disablePlayerById(params: PlayerModel): ResultDataBase<Int>
}
