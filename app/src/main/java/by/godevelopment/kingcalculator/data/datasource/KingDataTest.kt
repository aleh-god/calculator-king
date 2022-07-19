package by.godevelopment.kingcalculator.data.datasource

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.room.PrimaryKey
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.domain.commons.models.King
import by.godevelopment.kingcalculator.domain.commons.models.TakeTricks
import by.godevelopment.kingcalculator.domain.commons.models.TakeBoys
import by.godevelopment.kingcalculator.domain.commons.models.TakeGirls
import by.godevelopment.kingcalculator.domain.commons.models.TakeHearts
import by.godevelopment.kingcalculator.domain.commons.models.TakeKing
import by.godevelopment.kingcalculator.domain.commons.models.TakeLastTwo
import by.godevelopment.kingcalculator.domain.commons.models.TakeBFG
import by.godevelopment.kingcalculator.domain.commons.models.DoNotTakeTricks
import by.godevelopment.kingcalculator.domain.commons.models.DoNotTakeBoys
import by.godevelopment.kingcalculator.domain.commons.models.DoNotTakeGirls
import by.godevelopment.kingcalculator.domain.commons.models.DoNotTakeHearts
import by.godevelopment.kingcalculator.domain.commons.models.DoNotTakeKing
import by.godevelopment.kingcalculator.domain.commons.models.DoNotTakeLastTwo
import by.godevelopment.kingcalculator.domain.commons.models.DoNotTakeBFG

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

    fun getTypeNames(context: Context): List<String> = gameTypes.map {
        it.getDescription(context)
    }


}