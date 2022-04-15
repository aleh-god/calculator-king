package by.godevelopment.kingcalculator.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import by.godevelopment.kingcalculator.data.entities.PlayerProfile

@Database(entities = [PlayerProfile::class], version = 1, exportSchema = false)
abstract class KingDatabase: RoomDatabase() {

    abstract fun playersDao(): PlayersDao
}