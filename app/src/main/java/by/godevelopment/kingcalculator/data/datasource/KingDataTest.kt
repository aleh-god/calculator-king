package by.godevelopment.kingcalculator.data.datasource

import android.content.Context
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

    fun getTypeNames(context: Context): List<String> = gameTypes.map {
        it.getDescription(context)
    }
}