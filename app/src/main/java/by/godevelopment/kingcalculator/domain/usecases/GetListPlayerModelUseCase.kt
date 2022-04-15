package by.godevelopment.kingcalculator.domain.usecases

import by.godevelopment.kingcalculator.domain.models.ItemPlayerModel
import by.godevelopment.kingcalculator.domain.repositories.PlayerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListPlayerModelUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    operator fun invoke(): Flow<List<ItemPlayerModel>> {
        return playerRepository.getAllPlayers()
    }
}
