package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.data.SourceTestData
import by.godevelopment.kingcalculator.domain.partiesdomain.models.ItemPartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPartyModelItemsUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {
    operator fun invoke(): Flow<List<ItemPartyModel>> = flow {
        emit(SourceTestData.listParties)
    }
}