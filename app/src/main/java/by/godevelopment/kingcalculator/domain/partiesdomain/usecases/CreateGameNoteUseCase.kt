package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import javax.inject.Inject

class CreateGameNoteUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {
    suspend operator fun invoke(partyId: Long, gameType: GameType): ResultDataBase<Long> =
        partyRepository.createGameNote(
            partyId = partyId,
            gameType = gameType
        )
}