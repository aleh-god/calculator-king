package by.godevelopment.kingcalculator.domain.playersdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.playersdomain.models.PartyModel
import javax.inject.Inject

class GetPlayerTotalPartiesUseCase @Inject constructor() {

    val partiesRes = R.string.use_case_type_parties

    operator fun invoke(parties: List<PartyModel>): String {
        return parties.size.toString()
    }
}
