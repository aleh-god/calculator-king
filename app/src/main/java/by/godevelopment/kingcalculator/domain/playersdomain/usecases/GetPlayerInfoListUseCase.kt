package by.godevelopment.kingcalculator.domain.playersdomain.usecases

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.playersdomain.models.ItemPlayerInfoModel
import by.godevelopment.kingcalculator.domain.playersdomain.repositories.PlayerRepository
import javax.inject.Inject

class GetPlayerInfoListUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val getPlayerTotalPartiesUseCase: GetPlayerTotalPartiesUseCase,
    private val getPlayerTotalGamesUseCase: GetPlayerTotalGamesUseCase,
    private val getPlayerTotalScoreUseCase: GetPlayerTotalScoreUseCase,
    private val getPlayerGameWinPercentListUseCase: GetPlayerGameWinPercentListUseCase,
    private val getPlayerGamesScoreListUseCase: GetPlayerGamesScoreListUseCase
) {
    suspend operator fun invoke(playerId: Long): ResultDataBase<List<ItemPlayerInfoModel>> {
        val partiesResult = playerRepository.getAllPartiesByPlayerId(playerId)
        return when (partiesResult) {
            is ResultDataBase.Error -> ResultDataBase.Error(message = partiesResult.message)
            is ResultDataBase.Success -> {
                val parties = partiesResult.value
                val gamesResult = playerRepository.getAllGamesByPartyId(parties.map { it.id })
                when (gamesResult) {
                    is ResultDataBase.Error -> ResultDataBase.Error(message = gamesResult.message)
                    is ResultDataBase.Success -> {
                        val games = gamesResult.value
                        val tricksResult = playerRepository.getAllTricksByPlayerId(playerId)
                        when (tricksResult) {
                            is ResultDataBase.Error -> ResultDataBase.Error(message = tricksResult.message)
                            is ResultDataBase.Success -> {
                                val tricks = tricksResult.value
                                ResultDataBase.Success(
                                    value = listOf(
                                        ItemPlayerInfoModel(
                                            type = getPlayerTotalPartiesUseCase.partiesRes,
                                            value = getPlayerTotalPartiesUseCase(parties)
                                        ),
                                        ItemPlayerInfoModel(
                                            type = getPlayerTotalGamesUseCase.gamesRes,
                                            value = getPlayerTotalGamesUseCase(games)
                                        ),
                                        ItemPlayerInfoModel(
                                            type = getPlayerTotalScoreUseCase.scoreRes,
                                            value = getPlayerTotalScoreUseCase(tricks)
                                        )
                                    ) + getPlayerGameWinPercentListUseCase(
                                        tricks = tricks,
                                        games = games
                                    ) + getPlayerGamesScoreListUseCase(
                                        tricks = tricks,
                                        games = games
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
