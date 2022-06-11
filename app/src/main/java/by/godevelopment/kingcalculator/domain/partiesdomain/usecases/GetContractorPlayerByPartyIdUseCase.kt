package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import javax.inject.Inject

class GetContractorPlayerByPartyIdUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {

    suspend operator fun invoke(partyId: Long): String {
        return "Michelangelo"
    }
}