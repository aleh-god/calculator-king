package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.domain.partiesdomain.models.PlayersInPartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import javax.inject.Inject

class GetPlayersByPartyIdUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {

    suspend operator fun invoke(partyId: Long): PlayersInPartyModel {
        return PlayersInPartyModel(
            "Leonardo",
            "Raphael",
            "Donatello",
            "Michelangelo"
        )
    }
}