package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.domain.partiesdomain.models.PlayersInPartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import javax.inject.Inject

class GetPlayersByPartyIdUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {

    suspend operator fun invoke(partyId: Long): PlayersInPartyModel {

        val trimList = partyRepository.getPlayersByPartyId(partyId).map {
            it.take(4)
        }
        return PlayersInPartyModel(
            trimList[0],
            trimList[1],
            trimList[2],
            trimList[3]
        )
    }
}
