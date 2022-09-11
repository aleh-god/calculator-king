package by.godevelopment.kingcalculator.domain.settingsdomain.repositories

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase

interface DeletePlayersRepository {

    suspend fun deleteAllPlayers(): ResultDataBase<Int>
}
