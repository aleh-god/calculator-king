package by.godevelopment.kingcalculator.data.repositories

import android.util.Log
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.data.SourceTestData
import by.godevelopment.kingcalculator.domain.models.ItemPartyModel
import by.godevelopment.kingcalculator.domain.repositories.PartyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PartyRepositoryImpl @Inject constructor() : PartyRepository {

    override fun getParties(): Flow<List<ItemPartyModel>> = flow {
        emit(SourceTestData.listParties)
    }

    override suspend fun createNewPartyAndReturnId(playerEmails: Set<String>): Int {
        Log.i(TAG, "createNewPartyAndReturnId: $playerEmails")
        return 5
    }
}