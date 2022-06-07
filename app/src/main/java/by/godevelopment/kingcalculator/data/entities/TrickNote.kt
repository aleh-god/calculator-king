package by.godevelopment.kingcalculator.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tricks")
data class TrickNote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gameId: Long,
    val playerId: Long,
    val gameType: String,
    val tricksCount: Int
) {
    fun calculateScore(): Int {
        if (tricksCount !in 0..8) { return -1 }
        return when (gameType) {
            "TakeTricks" -> tricksCount * 5
            "TakeBoys" -> tricksCount * 5
            "TakeGirls" -> tricksCount * 10
            "TakeHearts" -> tricksCount * 5
            "TakeKing" -> tricksCount * 40
            "TakeLastTwo" -> tricksCount * 20
            "TakeBFG" -> 0
            "DoNotTakeTricks" -> tricksCount * -5
            "DoNotTakeBoys" -> tricksCount * -5
            "DoNotTakeGirls" -> tricksCount * -10
            "DoNotTakeHearts" -> tricksCount * -5
            "DoNotTakeKing" -> tricksCount * -40
            "DoNotTakeLastTwo" -> tricksCount * -20
            "DoNotTakeBFG" -> 0
            else -> -1
        }
    }
}