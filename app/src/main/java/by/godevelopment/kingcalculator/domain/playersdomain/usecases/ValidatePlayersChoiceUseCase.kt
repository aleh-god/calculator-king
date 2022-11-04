package by.godevelopment.kingcalculator.domain.playersdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.commons.models.ValidationResult
import javax.inject.Inject

class ValidatePlayersChoiceUseCase @Inject constructor() {

    private val UNIQUE_NAMES_LOWER_LIMIT = 4

    fun execute(playerNames: List<String>): ValidationResult {
        if (playerNames.any { it.isBlank() }) {
            return ValidationResult(successful = false)
        }
        val uniqueNames = playerNames.toSet().filterNot {
            it.isBlank()
        }
        if (uniqueNames.size < UNIQUE_NAMES_LOWER_LIMIT) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.message_error_validate_email_different
            )
        }
        return ValidationResult(successful = true)
    }
}
