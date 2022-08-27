package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.NUMBER_OF_ALL_KING_GAMES
import by.godevelopment.kingcalculator.commons.toDataString
import by.godevelopment.kingcalculator.domain.commons.helpers.KingHelper
import by.godevelopment.kingcalculator.domain.commons.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.partiesdomain.models.ItemPartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPartyModelItemsUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
    private val kingHelper: KingHelper,
    private val stringHelper: StringHelper
) {
    operator fun invoke(): Flow<List<ItemPartyModel>> {
        val deletedPlayer: PlayerModel = PlayerModel(
            id = 0,
            name = stringHelper.getString(R.string.player_null),
            email = stringHelper.getString(R.string.player_null),
            isActive = false
        )
        return partyRepository.getAllParties()
            .map { list ->
                list
                    .sortedByDescending { it.partyLastTime }
                    .map {
                    ItemPartyModel(
                        id = it.id,
                        partyName = it.partyName,
                        partyStartTime = it.startedAt.toDataString(),
                        partyLastTime = it.partyLastTime.toDataString(),
                        isComplete = it.partyGamesCount < NUMBER_OF_ALL_KING_GAMES,
                        partyGamesCount = it.partyGamesCount.toString(),
                        player_one = it.player_one ?: deletedPlayer,
                        player_one_score = kingHelper.calculateScoreToString(it.player_one_tricks),
                        player_two = it.player_two ?: deletedPlayer,
                        player_two_score = kingHelper.calculateScoreToString(it.player_two_tricks),
                        player_three = it.player_three ?: deletedPlayer,
                        player_three_score = kingHelper.calculateScoreToString(it.player_three_tricks),
                        player_four = it.player_four ?: deletedPlayer,
                        player_four_score = kingHelper.calculateScoreToString(it.player_four_tricks)
                    )
                }
            }
    }
}
