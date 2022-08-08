package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.commons.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.commons.models.mapResult
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import javax.inject.Inject

class GetContractorPlayerByPartyIdUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
    private val stringHelper: StringHelper
) {

    suspend operator fun invoke(partyId: Long): ResultDataBase<String> {
        return partyRepository.getContractorPlayerByPartyId(partyId).mapResult {
            buildString {
                append(it.name)
                append(stringHelper.getString(R.string.ui_text_contacts))
            }
//        return when(playerResult) {
//            is ResultDataBase.Error -> ResultDataBase.Error(message = playerResult.message)
//            is ResultDataBase.Success -> {
//                ResultDataBase.Success(
//                    value = buildString {
//                        append(playerResult.value)
//                        append(stringHelper.getString(R.string.ui_text_contacts))
//                    }
//                )
//            }
//        }
        }
    }
}
