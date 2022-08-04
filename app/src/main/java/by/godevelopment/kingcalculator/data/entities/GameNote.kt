package by.godevelopment.kingcalculator.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.godevelopment.kingcalculator.domain.commons.models.GameType

@Entity(tableName = "games")
data class GameNote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val partyId: Long,
    val gameType: GameType,
    val finishedAt: Long = 0
)
