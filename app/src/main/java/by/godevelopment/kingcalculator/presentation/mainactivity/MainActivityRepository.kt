package by.godevelopment.kingcalculator.presentation.mainactivity

interface MainActivityRepository {

    suspend fun deleteAllPartyNotes(): Int
}