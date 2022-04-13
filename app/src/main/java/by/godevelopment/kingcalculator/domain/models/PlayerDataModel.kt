package by.godevelopment.kingcalculator.domain.models

data class PlayerDataModel(
    val id: Int = 0,
    val name: String,
    val email: String,
    val avatar: Int? = null,
    val color: Int? = null
)