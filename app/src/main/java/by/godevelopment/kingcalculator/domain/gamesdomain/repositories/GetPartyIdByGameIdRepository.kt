package by.godevelopment.kingcalculator.domain.gamesdomain.repositories

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase

interface GetPartyIdByGameIdRepository {

    suspend fun getPartyIdByGameId(gameId: Long): ResultDataBase<Long>
}
