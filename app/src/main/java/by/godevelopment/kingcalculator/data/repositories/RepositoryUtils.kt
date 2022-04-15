package by.godevelopment.kingcalculator.data.repositories

import by.godevelopment.kingcalculator.commons.INT_NULL_VALUE
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.domain.models.ItemPlayerModel
import by.godevelopment.kingcalculator.domain.models.PlayerCardModel
import javax.inject.Inject

class RepositoryUtils @Inject constructor() {

    fun convertPlayerFromCardModelToProfile(playerCardModel: PlayerCardModel): PlayerProfile
            = PlayerProfile(
        id = playerCardModel.id,
        email = playerCardModel.email,
        name = playerCardModel.name,
        color = playerCardModel.color ?: INT_NULL_VALUE,
        avatar = playerCardModel.avatar ?: INT_NULL_VALUE
    )

    fun convertPlayerFromProfileToCardModel(playerProfile: PlayerProfile): PlayerCardModel
            = PlayerCardModel(
        id = playerProfile.id,
        email = playerProfile.email,
        name = playerProfile.name,
        avatar = playerProfile.avatar,
        color = playerProfile.color
    )

    fun convertPlayerFromProfileToItemModel(playerProfile: PlayerProfile): ItemPlayerModel
            = ItemPlayerModel(
        id = playerProfile.id,
        email = playerProfile.email,
        name = playerProfile.name,
        avatar = playerProfile.avatar,
        color = playerProfile.color
    )
}