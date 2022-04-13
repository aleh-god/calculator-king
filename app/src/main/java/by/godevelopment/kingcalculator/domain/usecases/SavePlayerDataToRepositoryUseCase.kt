package by.godevelopment.kingcalculator.domain.usecases

import by.godevelopment.kingcalculator.domain.models.PlayerDataModel
import by.godevelopment.kingcalculator.domain.repositories.PlayerRepository
import javax.inject.Inject

class SavePlayerDataToRepositoryUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) : BaseUseCase<Boolean, PlayerDataModel>() {
    override suspend fun run(params: PlayerDataModel): Boolean = playerRepository.saveNewPlayer(params)
}