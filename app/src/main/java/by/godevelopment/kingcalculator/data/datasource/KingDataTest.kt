package by.godevelopment.kingcalculator.data.datasource

import by.godevelopment.kingcalculator.commons.DELETED_STRING_VALUE
import by.godevelopment.kingcalculator.data.entities.GameNote
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.domain.commons.models.*
import java.util.*

object KingDataTest {

    private val gameTypes: List<King> = listOf(
        TakeTricks(),
        TakeBoys(),
        TakeGirls(),
        TakeHearts(),
        TakeKing(),
        TakeLastTwo(),
        TakeBFG(),
        DoNotTakeTricks(),
        DoNotTakeBoys(),
        DoNotTakeGirls(),
        DoNotTakeHearts(),
        DoNotTakeKing(),
        DoNotTakeLastTwo(),
        DoNotTakeBFG()
    )

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
        id = 1,
        email = "leo@leo.leo",
        name = player_leo_name,
        color = 0,
        avatar = 0
    )

    val playerRaphProfile = PlayerProfile(
        id = 2,
        email = "raph@raph.raph",
        name = player_raph_name,
        color = 0,
        avatar = 0
    )

    val playerDonProfile = PlayerProfile(
        id = 3,
        email = "don@don.don",
        name = player_don_name,
        color = 0,
        avatar = 0
    )

    val playerMichProfile = PlayerProfile(
        id = 4,
        email = "mich@mich.mich",
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

    private fun getRandomCalendar() = Calendar.getInstance().apply {
        val randomValue = Random().nextInt(12)
        set(
            2021,
            randomValue,
            randomValue,
            randomValue,
            randomValue,
            randomValue,
        )
    }

    val games  = listOf<GameNote>(
        GameNote(
            id = 0,
            partyId = 1,
            gameType = GameType.TakeTricks,
            finishedAt = getRandomCalendar().timeInMillis
        ),
        GameNote(
            id = 1,
            partyId = 1,
            gameType = GameType.TakeTricks,
            finishedAt = getRandomCalendar().timeInMillis
        ),
        GameNote(
            id = 2,
            partyId = 1,
            gameType = GameType.TakeTricks,
            finishedAt = getRandomCalendar().timeInMillis
        ),
        GameNote(
            id = 3,
            partyId = 1,
            gameType = GameType.TakeTricks,
            finishedAt = getRandomCalendar().timeInMillis
        ),
        GameNote(
            id = 4,
            partyId = 1,
            gameType = GameType.TakeBoys,
            finishedAt = getRandomCalendar().timeInMillis
        ),
        GameNote(
            id = 5,
            partyId = 1,
            gameType = GameType.TakeBoys,
            finishedAt = getRandomCalendar().timeInMillis
        ),
        GameNote(
            id = 6,
            partyId = 1,
            gameType = GameType.TakeGirls,
            finishedAt = getRandomCalendar().timeInMillis
        ),
        GameNote(
            id = 7,
            partyId = 1,
            gameType = GameType.TakeHearts,
            finishedAt = getRandomCalendar().timeInMillis
        ),
        GameNote(
            id = 8,
            partyId = 1,
            gameType = GameType.TakeHearts,
            finishedAt = getRandomCalendar().timeInMillis
        ),
        GameNote(
            id = 9,
            partyId = 1,
            gameType = GameType.TakeHearts,
            finishedAt = getRandomCalendar().timeInMillis
        ),
        GameNote(
            id = 10,
            partyId = 1,
            gameType = GameType.TakeKing,
            finishedAt = getRandomCalendar().timeInMillis
        )
    )
}
