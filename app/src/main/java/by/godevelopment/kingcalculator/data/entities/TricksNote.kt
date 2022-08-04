package by.godevelopment.kingcalculator.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.godevelopment.kingcalculator.domain.commons.models.GameType

@Entity(tableName = "tricks")
data class TricksNote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gameId: Long,
    val playerId: Long,
    val gameType: GameType,
    val tricksCount: Int
)