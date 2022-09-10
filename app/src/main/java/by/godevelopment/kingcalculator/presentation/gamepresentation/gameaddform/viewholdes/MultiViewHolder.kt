package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.viewholdes

import android.os.Bundle
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel

interface MultiViewHolder {

    fun bind(item: MultiItemModel, position: Int)

    fun update(bundle: Bundle)
}