package by.godevelopment.kingcalculator.data.entities

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import by.godevelopment.kingcalculator.commons.PLAYERS_TABLE_NAME
import by.godevelopment.kingcalculator.commons.LONG_ZERO_STUB

@Entity(tableName = PLAYERS_TABLE_NAME, indices = [Index(value = ["email"], unique = true), Index(value = ["name"], unique = true)])
data class PlayerProfile(
    @PrimaryKey(autoGenerate = true)
    val id: Long = LONG_ZERO_STUB,
    val email: String,
    val name: String,
    val isActive: Boolean = true,
    @ColorRes
    val avatar: Int? = null,
    @DrawableRes
    val color: Int? = null
)
