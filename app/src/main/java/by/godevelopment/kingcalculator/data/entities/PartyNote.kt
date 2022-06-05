package by.godevelopment.kingcalculator.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "parties", indices = [Index(value = ["partyName"], unique = true)])
data class PartyNote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val partyName: String,
    val startedAt: Long,
    val partyEndTime: Long? = null,
    val playerOneId: Long,
    val playerTwoId: Long,
    val playerThreeId: Long,
    val playerFourId: Long
)
