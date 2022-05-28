package by.godevelopment.kingcalculator.domain.repositories

import by.godevelopment.kingcalculator.domain.models.ItemPartyModel
import kotlinx.coroutines.flow.Flow

interface PartyRepository {

    fun getParties(): Flow<List<ItemPartyModel>>

    suspend fun createNewPartyAndReturnId(playerEmails: Set<String>): Int
}