package by.godevelopment.kingcalculator.presentation.mainactivity

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase

interface MainActivityRepository {

    suspend fun deleteAllPartyNotes(): ResultDataBase<Int>
}