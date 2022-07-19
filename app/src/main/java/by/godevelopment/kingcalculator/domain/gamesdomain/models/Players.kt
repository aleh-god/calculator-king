package by.godevelopment.kingcalculator.domain.gamesdomain.models

import androidx.annotation.StringRes
import by.godevelopment.kingcalculator.R

enum class Players(
    val id: Int,
    @StringRes
    val res: Int
) {
    PlayerOne(1, R.string.players_one),
    PlayerTwo(2, R.string.players_two),
    PlayerThree(3, R.string.players_three),
    PlayerFour(4, R.string.players_four)
}