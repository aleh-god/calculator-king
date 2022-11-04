package by.godevelopment.kingcalculator.domain.playersdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.commons.models.ValidationResult
import javax.inject.Inject

class ValidatePlayerNameUseCase @Inject constructor() {

    private val STRING_LOWER_LIMIT = 1
    private val PLAYER_NAME_MIN_LENGTH = 3

    operator fun invoke(playerName: String): ValidationResult {
        if (playerName.length in STRING_LOWER_LIMIT..PLAYER_NAME_MIN_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.message_error_validate_player_name_length
            )
        }
        return ValidationResult(successful = true)
    }
}
