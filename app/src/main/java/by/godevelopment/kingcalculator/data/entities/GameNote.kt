package by.godevelopment.kingcalculator.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.godevelopment.kingcalculator.commons.GAMES_TABLE_NAME
import by.godevelopment.kingcalculator.commons.LONG_ZERO_STUB
import by.godevelopment.kingcalculator.domain.commons.models.GameType

@Entity(tableName = GAMES_TABLE_NAME)
data class GameNote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = LONG_ZERO_STUB,
    val partyId: Long,
    val gameType: GameType,
    val finishedAt: Long = LONG_ZERO_STUB
)
