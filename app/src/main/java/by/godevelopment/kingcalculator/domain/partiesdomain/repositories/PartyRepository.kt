package by.godevelopment.kingcalculator.domain.partiesdomain.repositories

import by.godevelopment.kingcalculator.data.entities.PartyNote
import by.godevelopment.kingcalculator.domain.partiesdomain.models.ItemPartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.models.PartyModel
import kotlinx.coroutines.flow.Flow

interface PartyRepository {

    fun getAllParties(): Flow<List<PartyModel>>

    suspend fun createNewPartyAndReturnId(party: PartyNote): Long

    suspend fun getAllPlayersIdToNames(): Map<String, Long>
}