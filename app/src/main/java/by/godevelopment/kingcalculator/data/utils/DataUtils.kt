package by.godevelopment.kingcalculator.data.utils

import by.godevelopment.kingcalculator.commons.INT_NULL_VALUE
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.data.entities.TricksNote
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel

fun PlayerModel.toPlayerProfile(): PlayerProfile =
    PlayerProfile(
        id = this.id,
        email = this.email,
        name = this.name,
        color = this.color ?: INT_NULL_VALUE,
        avatar = this.avatar ?: INT_NULL_VALUE
    )

fun PlayerProfile.toPlayerModel(): PlayerModel =
    PlayerModel(
        id = this.id,
        email = this.email,
        name = this.name,
        avatar = this.avatar,
        color = this.color
    )

fun TricksNoteModel.toTricksNote(): TricksNote =
    TricksNote(
        id = id,
        gameId = gameId,
        playerId = playerId,
        gameType = gameType,
        tricksCount = tricksCount
    )
