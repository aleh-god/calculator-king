package by.godevelopment.kingcalculator.domain.commons.helpers

import by.godevelopment.kingcalculator.data.entities.TrickNote
import javax.inject.Inject

class KingHelper @Inject constructor()  {
    fun calculateScore(tricks: List<TrickNote>): String =
        tricks
            .sumOf { it.calculateScore() }
            .toString()
}