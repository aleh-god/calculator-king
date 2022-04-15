package by.godevelopment.kingcalculator.domain.repositories

import by.godevelopment.kingcalculator.domain.models.PartyModel
import kotlinx.coroutines.flow.Flow

interface PartyRepository {
    fun getParties(): Flow<PartyModel>
}