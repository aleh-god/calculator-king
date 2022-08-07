package by.godevelopment.kingcalculator.presentation.playerpresentation.playercard

import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel

data class CardUiState(
    val playerModel: PlayerModel,
    val playerNameError: Int? = null,
    val showsProgress: Boolean = false
)
