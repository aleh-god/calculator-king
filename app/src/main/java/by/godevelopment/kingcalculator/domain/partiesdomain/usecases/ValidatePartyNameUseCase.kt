package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.commons.models.ValidationResult
import javax.inject.Inject

class ValidatePartyNameUseCase @Inject constructor() {

    private val MIN_PARTY_NAME_LENGTH = 3
    private val MAX_PARTY_NAME_LENGTH = 20

    fun execute(partyName: String): ValidationResult {
        if (partyName.length < MIN_PARTY_NAME_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.message_error_validate_party_name_length
            )
        }
        if (partyName.length > MAX_PARTY_NAME_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.message_error_validate_party_name_high_length
            )
        }
        return ValidationResult(successful = true)
    }
}
