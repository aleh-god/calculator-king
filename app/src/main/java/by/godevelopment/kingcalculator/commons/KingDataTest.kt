package by.godevelopment.kingcalculator.commons

import by.godevelopment.kingcalculator.data.entities.GameNote
import by.godevelopment.kingcalculator.data.entities.PartyNote
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.data.entities.TricksNote
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import java.util.*

object KingDataTest {

    val player_leo_name: String = "Leonardo"
    val player_raph_name: String = "Raphael"
    val player_don_name: String = "Donatello"
    val player_mich_name: String = "Michelangelo"
    
    val playerLeoProfile = PlayerProfile(
        id = 0,
        email = "leo@sewers.splinter",
        name = player_leo_name,
        color = 0,
        avatar = 0
    )

    val playerRaphProfile = PlayerProfile(
        id = 0,
        email = "raph@sewers.splinter",
        name = player_raph_name,
        color = 0,
        avatar = 0
    )

    val playerDonProfile = PlayerProfile(
        id = 0,
        email = "don@sewers.splinter",
        name = player_don_name,
        color = 0,
        avatar = 0
    )

    val playerMichProfile = PlayerProfile(
        id = 0,
        email = "mich@sewers.splinter",
        name = player_mich_name,
        color = 0,
        avatar = 0
    )

    val players = listOf<PlayerProfile>(
        playerLeoProfile,
        playerRaphProfile,
        playerDonProfile,
        playerMichProfile
    )

    val party = PartyNote(
        id = 0,
        partyName = "Init sample",
        startedAt = System.currentTimeMillis(),
        partyLastTime = System.currentTimeMillis(),
        playerOneId = 1,
        playerTwoId = 2,
        playerThreeId = 3,
        playerFourId = 4
    )

    fun getGames(): List<GameNote> {
        val p1 = GameType.values().toList().shuffled()
        val p2 = GameType.values().toList().shuffled()
        val p3 = GameType.values().toList().shuffled()
        val p4 = GameType.values().toList().shuffled()
        return (0..13)
            .map { listOf(p1[it],p2[it],p3[it],p4[it]) }
            .flatten()
            .map { gameType ->
                GameNote(
                    id = 0L,
                    partyId = 1,
                    gameType = gameType,
                    finishedAt = System.currentTimeMillis()
                )
            }
    }

    fun getGamesIndexed(): List<GameNote> {
        val p1 = GameType.values().toList().shuffled()
        val p2 = GameType.values().toList().shuffled()
        val p3 = GameType.values().toList().shuffled()
        val p4 = GameType.values().toList().shuffled()
        return (0..13)
            .map { listOf(p1[it],p2[it],p3[it],p4[it]) }
            .flatten()
            .mapIndexed { index, gameType ->
                GameNote(
                    id = (index + 1).toLong(),
                    partyId = 1,
                    gameType = gameType,
                    finishedAt = System.currentTimeMillis()
                )
            }
    }

    fun getTricks() = getGamesIndexed()
        .flatMap { game ->
            when (game.gameType) {
                GameType.DoNotTakeBFG -> {
                    GameType.values()
                        .toList()
                        .filterNot { it == GameType.DoNotTakeBFG || it.trickScores > 0 }
                        .flatMap { gameType ->
                            val randomWinner = Random().nextInt(3) + 1
                            (1..4).map { player ->
                                TricksNote(
                                    id = 0,
                                    gameId = game.id,
                                    playerId = player.toLong(),
                                    gameType = gameType,
                                    tricksCount = if (player == randomWinner) gameType.tricksCount else 0
                                )
                            }
                        }
                }
                GameType.TakeBFG -> {
                    GameType.values()
                        .toList()
                        .filterNot { it.trickScores < 0 || it == GameType.TakeBFG }
                        .flatMap { gameType ->
                            val randomWinner = Random().nextInt(3) + 1
                            (1..4).map { player ->
                                TricksNote(
                                    id = 0,
                                    gameId = game.id,
                                    playerId = player.toLong(),
                                    gameType = gameType,
                                    tricksCount = if (player == randomWinner) gameType.tricksCount else 0
                                )
                            }
                        }
                }
                else -> {
                    val randomWinner = Random().nextInt(3) + 1
                    (1..4).map { player ->
                        TricksNote(
                            id = 0,
                            gameId = game.id,
                            playerId = player.toLong(),
                            gameType = game.gameType,
                            tricksCount = if (player == randomWinner) game.gameType.tricksCount else 0
                        )
                    }
                }
            }
        }
}
