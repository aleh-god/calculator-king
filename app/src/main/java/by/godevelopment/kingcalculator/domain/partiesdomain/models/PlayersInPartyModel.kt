package by.godevelopment.kingcalculator.domain.partiesdomain.models

import by.godevelopment.kingcalculator.commons.EMPTY_STRING

data class PlayersInPartyModel(
    val playerOne: String = EMPTY_STRING,
    val playerTwo: String = EMPTY_STRING,
    val playerThree: String = EMPTY_STRING,
    val playerFour: String = EMPTY_STRING
)
