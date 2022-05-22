package by.godevelopment.kingcalculator.domain.usecases.validationusecase

import android.util.Patterns
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.models.ValidationResult
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
    private val stringHelper: StringHelper
) {

    fun execute(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = stringHelper.getString(R.string.message_error_validate_email_blank)
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = stringHelper.getString(R.string.message_error_validate_email)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}