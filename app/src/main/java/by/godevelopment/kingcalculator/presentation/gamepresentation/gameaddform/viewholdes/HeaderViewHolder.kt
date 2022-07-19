package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.viewholdes

import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.databinding.HeaderGameAddFormBinding
import by.godevelopment.kingcalculator.domain.gamesdomain.models.FooterItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.HeaderItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel

class HeaderViewHolder(
    private val binding: HeaderGameAddFormBinding
) : RecyclerView.ViewHolder(binding.root), MultiViewHolder {
    override fun bind(item: MultiItemModel) {
        when(item) {
            is HeaderItemModel -> {
                val text = binding.root.context.getString(item.playerNumberRes)
                binding.headerText.text = text + " " + item.player.name
            }
            else -> {}
        }
    }
}