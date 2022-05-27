package by.godevelopment.kingcalculator.domain.usecases

import by.godevelopment.kingcalculator.data.SourceTestData
import by.godevelopment.kingcalculator.domain.models.ItemPartyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetListPartyModelUseCase @Inject constructor() {
    operator fun invoke(): Flow<List<ItemPartyModel>> = flow {
        emit(SourceTestData.listParties)
    }
}