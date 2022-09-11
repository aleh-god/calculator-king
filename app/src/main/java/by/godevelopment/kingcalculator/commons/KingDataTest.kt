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

    val nullPlayerProfile = PlayerProfile(
        name = DELETED_STRING_VALUE,
        email = DELETED_STRING_VALUE,
        avatar = 0,
        color = 0
    )

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

    val games = (0..3)
        .flatMap { GameType.values().toList() }
        .mapIndexed { index, gameType ->
            GameNote(
                id = 0,
                partyId = 1,
                gameType = gameType,
                finishedAt = System.currentTimeMillis()
            )
        }

    private val gamesX = (0..3)
        .flatMap { GameType.values().toList() }
        .mapIndexed { index, gameType ->
            GameNote(
                id = (index + 1).toLong(),
                partyId = 1,
                gameType = gameType,
                finishedAt = System.currentTimeMillis()
            )
        }

    val tricks = gamesX.flatMap { game ->
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

    private fun getRandomCalendar() = Calendar.getInstance().apply {
        val randomValue = Random().nextInt(12)
        set(
            2021,
            randomValue,
            randomValue,
            randomValue,
            randomValue,
            randomValue
        )
    }
}
