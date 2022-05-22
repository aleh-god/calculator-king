package by.godevelopment.kingcalculator.domain.models

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)