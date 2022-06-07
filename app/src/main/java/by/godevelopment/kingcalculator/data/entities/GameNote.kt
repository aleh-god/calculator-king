package by.godevelopment.kingcalculator.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameNote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val partyId: Long,
    val betPlayerId: Long,
    val finishedAt: Long
)
