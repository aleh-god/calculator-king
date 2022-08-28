package by.godevelopment.kingcalculator.data.repositories

import by.godevelopment.kingcalculator.data.datasource.TricksDataSource
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.settingsdomain.repositories.DeleteTricksRepository
import javax.inject.Inject

class TricksRepositoryImpl @Inject constructor(
    private val tricksDataSource: TricksDataSource
): DeleteTricksRepository {

    override suspend fun deleteAllTricks(): ResultDataBase<Int> =
        tricksDataSource.deleteAllTricks()
}
