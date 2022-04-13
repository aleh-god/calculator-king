package by.godevelopment.kingcalculator.domain.usecases

import by.godevelopment.kingcalculator.data.SourceTestData
import by.godevelopment.kingcalculator.domain.models.ItemPlayerModel
import javax.inject.Inject

class GetListPlayerModelUseCase @Inject constructor(

) : BaseUseCase<List<ItemPlayerModel>, EmptyParams>() {
    override suspend fun run(params: EmptyParams): List<ItemPlayerModel> {
        return SourceTestData.listPlayers
    }
}
