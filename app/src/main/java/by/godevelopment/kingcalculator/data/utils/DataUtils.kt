package by.godevelopment.kingcalculator.data.utils

import by.godevelopment.kingcalculator.commons.INT_NULL_VALUE
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.domain.models.ItemPlayerModel
import by.godevelopment.kingcalculator.domain.models.PlayerCardModel

fun PlayerCardModel.toPlayerProfile(): PlayerProfile =
    PlayerProfile(
        id = this.id,
        email = this.email,
        name = this.name,
        color = this.color ?: INT_NULL_VALUE,
        avatar = this.avatar ?: INT_NULL_VALUE
    )

fun PlayerProfile.toPlayerCardModel(): PlayerCardModel =
    PlayerCardModel(
        id = this.id,
        email = this.email,
        name = this.name,
        avatar = this.avatar,
        color = this.color
    )

fun PlayerProfile.toItemPlayerModel(): ItemPlayerModel =
    ItemPlayerModel(
        id = this.id,
        email = this.email,
        name = this.name,
        avatar = this.avatar,
        color = this.color
    )