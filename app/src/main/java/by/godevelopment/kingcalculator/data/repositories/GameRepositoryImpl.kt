package by.godevelopment.kingcalculator.data.repositories

import by.godevelopment.kingcalculator.data.datasource.GamesDataSource
import by.godevelopment.kingcalculator.data.datasource.KingDataTest
import by.godevelopment.kingcalculator.data.models.PlayersInGameModel
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.Players
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gamesDataSource: GamesDataSource
): GameRepository {

    private var temporaryGameId: Long? = null


    suspend fun createNewGameId(partyId: Long): Long {
        return gamesDataSource.getGamesCountByPartyId(partyId) + 1L
    }

    override fun getGames(): Flow<GameModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayersByGameId(key: Long): PlayersInGameModel {
        return PlayersInGameModel(
            gameId = key,
            playersMap = hashMapOf(
                Players.PlayerOne to KingDataTest.playerLeoProfile,
                Players.PlayerTwo to KingDataTest.playerDonProfile,
                Players.PlayerThree to KingDataTest.playerMichProfile,
                Players.PlayerFour to KingDataTest.playerRaphProfile
            )
        )
    }

    override suspend fun getGameTypeByGameId(key: Long): GameType {
        return GameType.TakeTricks
    }
}