package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GameRepository
import javax.inject.Inject

class GetGameTypeByGameIdUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    suspend operator fun invoke(key: Long): GameType =
        gameRepository.getGameTypeByGameId(key)
}