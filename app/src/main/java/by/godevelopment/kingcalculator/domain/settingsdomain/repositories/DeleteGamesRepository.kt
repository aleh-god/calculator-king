package by.godevelopment.kingcalculator.domain.settingsdomain.repositories

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase

interface DeleteGamesRepository {

    suspend fun deleteAllGames(): ResultDataBase<Int>
}
