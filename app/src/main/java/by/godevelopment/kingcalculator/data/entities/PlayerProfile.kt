package by.godevelopment.kingcalculator.data.entities

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "players", indices = [Index(value = ["email"], unique = true), Index(value = ["name"], unique = true)])
data class PlayerProfile(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val email: String,
    val name: String,
    @ColorRes
    val color: Int,
    @DrawableRes
    val avatar: Int
)
