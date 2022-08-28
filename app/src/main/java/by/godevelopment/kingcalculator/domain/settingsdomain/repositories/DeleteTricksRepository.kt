package by.godevelopment.kingcalculator.domain.settingsdomain.repositories

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase

interface DeleteTricksRepository {

    suspend fun deleteAllTricks(): ResultDataBase<Int>
}
