package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.commons.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.commons.models.ValidationResult
import javax.inject.Inject

class ValidatePartyNameUseCase @Inject constructor(
    private val stringHelper: StringHelper
)  {

    fun execute(partyName: String): ValidationResult {
        if(partyName.length < 3) {
            return ValidationResult(
                successful = false,
                errorMessage = stringHelper.getString(
                    R.string.message_error_validate_party_name_length
                )
            )
        }
        return ValidationResult(successful = true)
    }
}