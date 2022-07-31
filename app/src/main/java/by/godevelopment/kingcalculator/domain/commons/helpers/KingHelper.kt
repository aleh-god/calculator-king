package by.godevelopment.kingcalculator.domain.commons.helpers

import by.godevelopment.kingcalculator.data.entities.TricksNote
import javax.inject.Inject

class KingHelper @Inject constructor()  {
    fun calculateScore(tricks: List<TricksNote>): String =
        tricks
            .sumOf { it.gameType.getTotalGameScore(it.tricksCount) }
            .toString()
}