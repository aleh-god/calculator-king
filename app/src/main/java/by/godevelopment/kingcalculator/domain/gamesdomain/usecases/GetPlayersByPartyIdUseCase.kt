package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import by.godevelopment.kingcalculator.data.models.PlayersInGameModel
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GameRepository
import javax.inject.Inject

class GetPlayersByPartyIdUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    suspend operator fun invoke(key: Long): ResultDataBase<PlayersInGameModel> =
        gameRepository.getPlayersByPartyId(key)
}