package by.godevelopment.kingcalculator.domain.commons.models

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)