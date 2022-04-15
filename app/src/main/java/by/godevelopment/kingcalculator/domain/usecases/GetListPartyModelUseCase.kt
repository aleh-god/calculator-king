package by.godevelopment.kingcalculator.domain.usecases

import by.godevelopment.kingcalculator.data.SourceTestData
import by.godevelopment.kingcalculator.domain.models.ItemPartyModel
import javax.inject.Inject

class GetListPartyModelUseCase @Inject constructor(

) : BaseUseCase<List<ItemPartyModel>, EmptyParams>() {
    override suspend fun run(params: EmptyParams): List<ItemPartyModel> {
        return SourceTestData.listParties
    }
}