package by.godevelopment.kingcalculator.domain.playersdomain.usecases

import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import by.godevelopment.kingcalculator.domain.playersdomain.repositories.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetListPlayerModelUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    operator fun invoke(): Flow<List<PlayerModel>> {
        return playerRepository.getAllPlayers().map { list ->
            list.filter { it.isActive }
        }
    }
}
