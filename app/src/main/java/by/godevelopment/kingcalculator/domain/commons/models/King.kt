package by.godevelopment.kingcalculator.domain.commons.models

import android.content.Context
import androidx.annotation.StringRes
import by.godevelopment.kingcalculator.R

sealed class King {

    abstract val id: Int
    abstract val trickScores: Int
    abstract val tricksCount: Int
    abstract val res: Int

    fun calculateScore(count: Int): Int {
        return if (count in 0..tricksCount) { count * trickScores } else { -1 }
    }

    fun getDescription(context: Context): String {
        return context.getString(res)
    }
}

class TakeTricks(override val id: Int = 1, override val trickScores: Int = 5, override val tricksCount: Int = 8, @StringRes override val res: Int = R.string.game_type_tt) : King()
class TakeBoys(override val id: Int = 2, override val trickScores: Int = 10, override val tricksCount: Int = 4, @StringRes override val res: Int = R.string.game_type_tb) : King()
class TakeGirls(override val id: Int = 3, override val trickScores: Int = 10, override val tricksCount: Int = 4, @StringRes override val res: Int = R.string.game_type_tg) : King()
class TakeHearts(override val id: Int = 4, override val trickScores: Int = 5, override val tricksCount: Int = 8, @StringRes override val res: Int = R.string.game_type_th) : King()
class TakeKing(override val id: Int = 5, override val trickScores: Int = 40, override val tricksCount: Int = 1, @StringRes override val res: Int = R.string.game_type_tk) : King()
class TakeLastTwo(override val id: Int = 6, override val trickScores: Int = 20, override val tricksCount: Int = 2, @StringRes override val res: Int = R.string.game_type_tl) : King()
class TakeBFG(override val id: Int = 7, override val trickScores: Int = 240, override val tricksCount: Int = 27, @StringRes override val res: Int = R.string.game_type_tfg) : King()
class DoNotTakeTricks(override val id: Int = -1, override val trickScores: Int = -5, override val tricksCount: Int = 8, @StringRes override val res: Int = R.string.game_type_ntt) : King()
class DoNotTakeBoys(override val id: Int = -2, override val trickScores: Int = -10, override val tricksCount: Int = 4, @StringRes override val res: Int = R.string.game_type_ntb) : King()
class DoNotTakeGirls(override val id: Int = -3, override val trickScores: Int = -10, override val tricksCount: Int = 4, @StringRes override val res: Int = R.string.game_type_ntg) : King()
class DoNotTakeHearts(override val id: Int = -4, override val trickScores: Int = -5, override val tricksCount: Int = 8, @StringRes override val res: Int = R.string.game_type_nth) : King()
class DoNotTakeKing(override val id: Int = -5, override val trickScores: Int = -40, override val tricksCount: Int = 1, @StringRes override val res: Int = R.string.game_type_ntk) : King()
class DoNotTakeLastTwo(override val id: Int = -6, override val trickScores: Int = -20, override val tricksCount: Int = 2, @StringRes override val res: Int = R.string.game_type_ntl) : King()
class DoNotTakeBFG(override val id: Int = -7, override val trickScores: Int = -240, override val tricksCount: Int = 27, @StringRes override val res: Int = R.string.game_type_ntfg) : King()
