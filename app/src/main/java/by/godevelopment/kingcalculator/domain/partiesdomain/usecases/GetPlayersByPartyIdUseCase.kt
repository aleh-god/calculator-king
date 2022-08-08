package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.commons.DELETED_STRING_VALUE
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.Players
import by.godevelopment.kingcalculator.domain.partiesdomain.models.PlayersInPartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import javax.inject.Inject

class GetPlayersByPartyIdUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {

    suspend operator fun invoke(partyId: Long): ResultDataBase<PlayersInPartyModel> {

        val result = partyRepository.getPlayersByPartyId(partyId)
        return when(result) {
            is ResultDataBase.Error -> { ResultDataBase.Error(message = result.message) }
            is ResultDataBase.Success -> {
                val map = result.value

                fun takeShortName(players: Players): String =
                    (map[players]?.name ?: DELETED_STRING_VALUE).take(4)

                ResultDataBase.Success(
                    value = PlayersInPartyModel(
                        playerOne = takeShortName(Players.PlayerOne),
                        playerTwo = takeShortName(Players.PlayerTwo),
                        playerThree = takeShortName(Players.PlayerThree),
                        playerFour = takeShortName(Players.PlayerFour)
                    )
                )
            }
        }
    }
}
