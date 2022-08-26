package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import javax.inject.Inject

class DeletePartyUseCase @Inject constructor(
    private val partyRepository: PartyRepository
){

    suspend operator fun invoke(partyId: Long): ResultDataBase<Int> =
        partyRepository.deletePartyById(partyId)
}