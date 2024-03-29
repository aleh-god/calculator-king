package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.commons.utils.mapResult
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import javax.inject.Inject

class GetContractorPlayerByPartyIdUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {

    suspend operator fun invoke(partyId: Long): ResultDataBase<String> {
        return partyRepository.getContractorPlayerByPartyId(partyId).mapResult { it.name }
    }
}
