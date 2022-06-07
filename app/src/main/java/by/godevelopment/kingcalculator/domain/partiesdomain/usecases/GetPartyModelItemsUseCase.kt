package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.commons.NUMBER_OF_ALL_KING_GAMES
import by.godevelopment.kingcalculator.commons.toDataString
import by.godevelopment.kingcalculator.domain.commons.helpers.KingHelper
import by.godevelopment.kingcalculator.domain.partiesdomain.models.ItemPartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.models.PartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPartyModelItemsUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
    private val kingHelper: KingHelper
) {
    operator fun invoke(): Flow<List<ItemPartyModel>> {
        val partyModels = partyRepository.getAllParties().map { list ->
            list.map {
                val gamesCount = calculateGamesCount(it)
                ItemPartyModel(
                    id = it.id,
                    partyName = it.partyName,
                    partyStartDate = it.startedAt.toDataString(),
                    partyLastDate = "05 october 2022", // TODO()
                    isComplete = gamesCount < NUMBER_OF_ALL_KING_GAMES,
                    partyGamesCount = gamesCount.toString(),
                    player_one_name = it.player_one_name,
                    player_one_score = kingHelper.calculateScore(it.player_one_tricks),
                    player_two_name = it.player_two_name,
                    player_two_score = kingHelper.calculateScore(it.player_two_tricks),
                    player_three_name = it.player_three_name,
                    player_three_score = kingHelper.calculateScore(it.player_three_tricks),
                    player_four_name = it.player_four_name,
                    player_four_score = kingHelper.calculateScore(it.player_four_tricks)
                )
            }
        }
        return partyModels
    }

    private fun calculateGamesCount(party: PartyModel): Int {
        return listOf(
            party.player_one_tricks.size,
            party.player_two_tricks.size,
            party.player_three_tricks.size,
            party.player_four_tricks.size
        ).maxOf { it }
    }
}
