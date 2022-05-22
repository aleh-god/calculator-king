package by.godevelopment.kingcalculator.domain.usecases.validationusecase

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.models.ValidationResult
import javax.inject.Inject

class ValidatePlayerNameUseCase @Inject constructor(
    private val stringHelper: StringHelper
)  {

    fun execute(playerName: String): ValidationResult {
        if(playerName.length < 3) {
            return ValidationResult(
                successful = false,
                errorMessage = stringHelper.getString(R.string.message_error_validate_email_length)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}