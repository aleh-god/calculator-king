package by.godevelopment.kingcalculator.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.godevelopment.kingcalculator.data.entities.GameNote
import by.godevelopment.kingcalculator.data.entities.PartyNote
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.data.entities.TricksNote

@Database(entities = [PlayerProfile::class, GameNote::class, PartyNote::class, TricksNote::class], version = 1, exportSchema = false)
abstract class KingDatabase: RoomDatabase() {

    abstract fun playersDao(): PlayersDao

    abstract fun partiesDao(): PartiesDao

    abstract fun gamesDao(): GamesDao

    abstract fun tricksDao(): TricksDao
}