package by.godevelopment.kingcalculator.domain.playersdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel
import javax.inject.Inject

class GetPlayerTotalScoreUseCase @Inject constructor() {

    val scoreRes = R.string.use_case_type_score

    operator fun invoke(parties: List<TricksNoteModel>): String {
        return parties.sumOf { it.gameType.getTotalGameScore(it.tricksCount) }.toString()
    }
}
