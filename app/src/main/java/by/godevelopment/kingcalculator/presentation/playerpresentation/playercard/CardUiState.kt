package by.godevelopment.kingcalculator.presentation.playerpresentation.playercard

import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerCardModel

data class CardUiState(
    val playerCardModel: PlayerCardModel,
    val playerNameError: String? = null,
    val showsProgress: Boolean = false
)
