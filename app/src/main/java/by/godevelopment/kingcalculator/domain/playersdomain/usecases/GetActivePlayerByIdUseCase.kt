package by.godevelopment.kingcalculator.domain.playersdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.commons.utils.flatMapResult
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import by.godevelopment.kingcalculator.domain.playersdomain.repositories.PlayerRepository
import javax.inject.Inject

class GetActivePlayerByIdUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(idPlayer: Long): ResultDataBase<PlayerModel> =
        playerRepository.getPlayerById(idPlayer).flatMapResult {
            if (it.isActive) ResultDataBase.Success(value = it)
            else ResultDataBase.Error(message = R.string.message_error_player_id)
        }
}
