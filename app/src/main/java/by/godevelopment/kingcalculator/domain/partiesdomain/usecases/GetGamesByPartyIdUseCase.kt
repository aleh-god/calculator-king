package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import android.content.Context
import android.util.Log
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.data.datasource.KingDataTest
import by.godevelopment.kingcalculator.domain.partiesdomain.models.GamesTableItemModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

class GetGamesByPartyIdUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
    @ApplicationContext
    private val context: Context
) {

    operator fun invoke(partyId: Long): Flow<List<GamesTableItemModel>> {
        val typeNames = KingDataTest.getTypeNames(context)
        Log.i(TAG, "GetGamesUseCaseByPartyId invoke: $partyId")
        val result = mutableListOf<GamesTableItemModel>()
        (1..1L).forEach { x ->
            (0..13).forEach { y ->
                result.add(
                    GamesTableItemModel(
                        id = x * (y + 1),
                        gameTypeName = typeNames[y],
                        isFinishedOneGame = Random.nextBoolean(),
                        isFinishedTwoGame = Random.nextBoolean(),
                        isFinishedThreeGame = Random.nextBoolean(),
                        isFinishedFourGame = Random.nextBoolean(),
                        openedColumnNumber = 4
                    )
                )
            }
        }
        return flow {
            emit(result)
        }
    }
}