package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GameRepository
import javax.inject.Inject

class GetGameNoteByIdUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    suspend operator fun invoke(gameId: Long): ResultDataBase<GameModel> =
        gameRepository.getGameNoteById(gameId)
}
