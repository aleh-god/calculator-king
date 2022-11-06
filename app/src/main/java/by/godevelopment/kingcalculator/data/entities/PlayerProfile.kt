package by.godevelopment.kingcalculator.data.entities

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import by.godevelopment.kingcalculator.commons.PLAYERS_TABLE_NAME
import by.godevelopment.kingcalculator.commons.LONG_ZERO_STUB

@Entity(tableName = PLAYERS_TABLE_NAME, indices = [Index(value = ["real_name"], unique = true), Index(value = ["party_name"], unique = true)])
data class PlayerProfile(
    @PrimaryKey(autoGenerate = true)
    val id: Long = LONG_ZERO_STUB,
    @ColumnInfo(name = "party_name") val name: String,
    @ColumnInfo(name = "real_name") val realName: String,
    val isActive: Boolean = true,
    @DrawableRes
    val avatar: Int? = null,
    @ColorRes
    val color: Int? = null
)
