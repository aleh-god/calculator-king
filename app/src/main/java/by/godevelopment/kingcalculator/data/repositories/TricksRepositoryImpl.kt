package by.godevelopment.kingcalculator.data.repositories

import by.godevelopment.kingcalculator.data.datasource.TricksDataSource
import by.godevelopment.kingcalculator.di.IoDispatcher
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.settingsdomain.repositories.DeleteTricksRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TricksRepositoryImpl @Inject constructor(
    private val tricksDataSource: TricksDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): DeleteTricksRepository {

    override suspend fun deleteAllTricks(): ResultDataBase<Int> = withContext(ioDispatcher) {
        tricksDataSource.deleteAllTricks()
    }
}
