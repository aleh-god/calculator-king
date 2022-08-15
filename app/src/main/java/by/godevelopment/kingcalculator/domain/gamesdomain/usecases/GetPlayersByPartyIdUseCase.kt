package by.godevelopment.kingcalculator.domain.gamesdomain.usecases

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.Players
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GameRepository
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import javax.inject.Inject

class GetPlayersByPartyIdUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    suspend operator fun invoke(key: Long): ResultDataBase<Map<Players, PlayerModel>> =
        gameRepository.getPlayersByPartyId(key)
}
