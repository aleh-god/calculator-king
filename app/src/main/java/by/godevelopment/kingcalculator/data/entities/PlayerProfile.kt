package by.godevelopment.kingcalculator.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "players", indices = [Index(value = ["email"], unique = true)])
data class PlayerProfile(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String,
    val name: String,
    val color: Int,
    val avatar: Int
)
