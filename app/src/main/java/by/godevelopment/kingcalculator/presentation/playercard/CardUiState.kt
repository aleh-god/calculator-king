package by.godevelopment.kingcalculator.presentation.playercard

import by.godevelopment.kingcalculator.domain.models.PlayerCardModel

data class CardUiState(
    val playerCardModel: PlayerCardModel,
    val playerNameError: String? = null,
    val showsProgress: Boolean = false
)
