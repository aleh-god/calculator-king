package by.godevelopment.kingcalculator.domain.gamesdomain.models

data class ListValidationResult(
    val successful: Boolean,
    val errorMessage: Int? = null,
    val errorList: List<MultiItemModel>? = null
)