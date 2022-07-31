package by.godevelopment.kingcalculator.domain.playersdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.commons.models.ValidationResult
import javax.inject.Inject

class ValidatePlayerNameUseCase @Inject constructor()  {

    fun execute(playerName: String): ValidationResult {
        if(playerName.length < 3) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.message_error_validate_player_name_length
            )
        }
        return ValidationResult(successful = true)
    }
}