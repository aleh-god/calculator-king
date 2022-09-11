package by.godevelopment.kingcalculator.domain.commons.models

import android.content.Context
import androidx.annotation.StringRes
import by.godevelopment.kingcalculator.R

enum class GameType(
    val id: Int,
    val trickScores: Int,
    val tricksCount: Int,
    @StringRes
    val res: Int
) {
    TakeTricks(1, 5, 8, R.string.game_type_tt),
    TakeBoys(2, 5, 8, R.string.game_type_tb),
    TakeGirls(3, 10, 4, R.string.game_type_tg),
    TakeHearts(4, 5, 8, R.string.game_type_th),
    TakeKing(5, 40, 1, R.string.game_type_tk),
    TakeLastTwo(6, 20, 2, R.string.game_type_tl),
    TakeBFG(7, 240, 31, R.string.game_type_tfg),
    DoNotTakeTricks(11, -5, 8, R.string.game_type_ntt),
    DoNotTakeBoys(12, -5, 8, R.string.game_type_ntb),
    DoNotTakeGirls(13, -10, 4, R.string.game_type_ntg),
    DoNotTakeHearts(14, -5, 8, R.string.game_type_nth),
    DoNotTakeKing(15, -40, 1, R.string.game_type_ntk),
    DoNotTakeLastTwo(16, -20, 2, R.string.game_type_ntl),
    DoNotTakeBFG(17, -240, 31, R.string.game_type_ntfg);

    fun getTotalGameScore(countTricks: Int): Int {
        return countTricks * this.trickScores
    }

    fun getDescription(context: Context): String {
        return context.getString(res)
    }

    companion object {
        fun getGameTypeByIdOrNull(key: Int): GameType? =
            GameType.values().firstOrNull { key == it.id }
    }
}
