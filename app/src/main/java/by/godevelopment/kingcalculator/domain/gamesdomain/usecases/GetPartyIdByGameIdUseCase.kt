package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GameRepository
import javax.inject.Inject

class GetPartyIdByGameIdUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(gameId: Long): ResultDataBase<Long> {
        return gameRepository.getPartyIdByGameId(gameId)
    }
}