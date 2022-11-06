package by.godevelopment.kingcalculator.commons

import by.godevelopment.kingcalculator.data.entities.PlayerProfile

object KingDataTest {

    val player_leo_name: String = "Leonardo"
    val player_raph_name: String = "Raphael"
    val player_don_name: String = "Donatello"
    val player_mich_name: String = "Michelangelo"

    val playerLeoProfile = PlayerProfile(
        id = 0,
        realName = "da Vinci",
        name = player_leo_name,
        color = 0,
        avatar = 0
    )

    val playerRaphProfile = PlayerProfile(
        id = 0,
        realName = "Santi",
        name = player_raph_name,
        color = 0,
        avatar = 0
    )

    val playerDonProfile = PlayerProfile(
        id = 0,
        realName = "di Niccolo",
        name = player_don_name,
        color = 0,
        avatar = 0
    )

    val playerMichProfile = PlayerProfile(
        id = 0,
        realName = "di Lodovico",
        name = player_mich_name,
        color = 0,
        avatar = 0
    )
}
