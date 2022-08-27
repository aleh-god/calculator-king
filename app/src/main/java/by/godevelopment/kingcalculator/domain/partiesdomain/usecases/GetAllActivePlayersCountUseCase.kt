package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import javax.inject.Inject

class GetAllActivePlayersCountUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {

    suspend operator fun invoke(): ResultDataBase<Int> {
        return partyRepository.getAllPlayersCount()
    }
}