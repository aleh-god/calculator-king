package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.commons.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.commons.models.Players
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.partiesdomain.models.PlayersInPartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import javax.inject.Inject

class GetPlayersByPartyIdUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
    private val stringHelper: StringHelper
) {

    private val SHORT_NAME_LENGTH = 4

    suspend operator fun invoke(partyId: Long): ResultDataBase<PlayersInPartyModel> {
        val result = partyRepository.getPlayersByPartyId(partyId)
        return when (result) {
            is ResultDataBase.Error -> { ResultDataBase.Error(message = result.message) }
            is ResultDataBase.Success -> {
                val map = result.value

                fun takeShortName(players: Players): String {
                    val player = map[players]
                    val name =
                        if (player != null && player.isActive) player.name
                        else stringHelper.getString(R.string.player_null)
                    return name.take(SHORT_NAME_LENGTH)
                }

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
