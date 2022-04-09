package by.godevelopment.kingcalculator.domain.usecases

import by.godevelopment.kingcalculator.domain.models.DataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDataUseCase @Inject constructor() {
    operator fun invoke(): Flow<List<DataModel>> = flow {
        emit(
            listOf(DataModel(0))
        )
    }
}