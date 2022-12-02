package by.godevelopment.kingcalculator.domain.playersdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.commons.models.ValidationResult
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ValidatePlayerNameUseCaseTest {

    @Test
    fun `invoke input correct name return Result = true`() {

        val useCase = ValidatePlayerNameUseCase()
        val result = useCase.invoke("user")
        val expect = ValidationResult(successful = true)
        assertEquals(expect, result)
    }

    @Test
    fun `invoke input empty string return message_error_validate_player_name_length`() {

        val useCase = ValidatePlayerNameUseCase()
        val result = useCase.invoke("")
        val expect = ValidationResult(
            successful = false,
            errorMessage = R.string.message_error_validate_player_name_length
        )
        assertEquals(expect, result)
    }

    @Test
    fun `invoke input 21 length string return message_error_validate_player_name_high_length`() {

        val useCase = ValidatePlayerNameUseCase()
        val result = useCase.invoke("123456789012345678901")
        val expected = ValidationResult(
            successful = false,
            errorMessage = R.string.message_error_validate_player_name_high_length
        )
        assertEquals(expected, result)
    }
}