package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.commons.NUMBER_OF_ALL_KING_GAMES
import by.godevelopment.kingcalculator.commons.toDataString
import by.godevelopment.kingcalculator.domain.commons.helpers.KingHelper
import by.godevelopment.kingcalculator.domain.partiesdomain.models.ItemPartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPartyModelItemsUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
    private val kingHelper: KingHelper
) {
    operator fun invoke(): Flow<List<ItemPartyModel>> {
        return partyRepository.getAllParties()
            .map { list ->
                list
                    .sortedByDescending { it.partyLastTime }
                    .map {
                    ItemPartyModel(
                        id = it.id,
                        partyName = it.partyName,
                        partyStartDate = it.startedAt.toDataString(),
                        partyLastDate = it.partyLastTime.toDataString(),
                        isComplete = it.partyGamesCount < NUMBER_OF_ALL_KING_GAMES,
                        partyGamesCount = it.partyGamesCount.toString(),
                        player_one = it.player_one,
                        player_one_score = kingHelper.calculateScoreToString(it.player_one_tricks),
                        player_two = it.player_two,
                        player_two_score = kingHelper.calculateScoreToString(it.player_two_tricks),
                        player_three = it.player_three,
                        player_three_score = kingHelper.calculateScoreToString(it.player_three_tricks),
                        player_four = it.player_four,
                        player_four_score = kingHelper.calculateScoreToString(it.player_four_tricks)
                    )
                }
            }
    }
}
