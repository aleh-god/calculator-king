package by.godevelopment.kingcalculator.domain.usecases.validationusecase

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.models.ValidationResult
import javax.inject.Inject

class ValidatePlayersChoiceUseCase @Inject constructor(
    private val stringHelper: StringHelper
){

    fun execute(playerNames: List<String>): ValidationResult {
        if (playerNames.any { it.isBlank() }) {
            return ValidationResult(successful = false)
        }
        val uniqueNames = playerNames.toSet().filterNot {
            it.isBlank()
        }
        if(uniqueNames.size < 4) {
            return ValidationResult(
                successful = false,
                errorMessage = stringHelper.getString(R.string.message_error_validate_email_different)
            )
        }
        return ValidationResult(successful = true)
    }
}
