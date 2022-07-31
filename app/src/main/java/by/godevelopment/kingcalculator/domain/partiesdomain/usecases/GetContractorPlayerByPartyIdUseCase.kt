package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.commons.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import javax.inject.Inject

class GetContractorPlayerByPartyIdUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
    private val stringHelper: StringHelper
) {
    // TODO("Rework UseCase)
    suspend operator fun invoke(partyId: Long): String {
        val playerName = partyRepository.getContractorPlayerByPartyId(partyId)
        return buildString {
            append(playerName)
            append(stringHelper.getString(R.string.ui_text_contacts))
        }
    }
}
