package by.godevelopment.kingcalculator.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import by.godevelopment.kingcalculator.commons.PARTIES_TABLE_NAME
import by.godevelopment.kingcalculator.commons.LONG_ZERO_STUB

@Entity(tableName = PARTIES_TABLE_NAME, indices = [Index(value = ["partyName"], unique = true)])
data class PartyNote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = LONG_ZERO_STUB,
    val partyName: String,
    val startedAt: Long,
    val partyLastTime: Long,
    val playerOneId: Long,
    val playerTwoId: Long,
    val playerThreeId: Long,
    val playerFourId: Long
)
