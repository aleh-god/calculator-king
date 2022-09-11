package by.godevelopment.kingcalculator.domain.playersdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.commons.models.ValidationResult
import javax.inject.Inject

class ValidatePlayerNameUseCase @Inject constructor() {

    operator fun invoke(playerName: String): ValidationResult {
        if (playerName.length in 1..3) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.message_error_validate_player_name_length
            )
        }
        return ValidationResult(successful = true)
    }
}
