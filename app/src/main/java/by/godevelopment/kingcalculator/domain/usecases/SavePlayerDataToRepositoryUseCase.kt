package by.godevelopment.kingcalculator.domain.usecases

import by.godevelopment.kingcalculator.domain.models.PlayerCardModel
import by.godevelopment.kingcalculator.domain.repositories.PlayerRepository
import javax.inject.Inject

class SavePlayerDataToRepositoryUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(params: PlayerCardModel): Boolean = playerRepository.saveNewPlayer(params)
}