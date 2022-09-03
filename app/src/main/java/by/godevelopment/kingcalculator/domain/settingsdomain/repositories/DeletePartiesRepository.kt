package by.godevelopment.kingcalculator.domain.settingsdomain.repositories

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase

interface DeletePartiesRepository {

    suspend fun deleteAllParties(): ResultDataBase<Int>
}
