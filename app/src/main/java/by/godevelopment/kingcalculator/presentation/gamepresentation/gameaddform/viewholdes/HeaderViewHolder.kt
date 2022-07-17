package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.viewholdes

import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.databinding.HeaderGameAddFormBinding
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel

class HeaderViewHolder(
    private val binding: HeaderGameAddFormBinding
) : RecyclerView.ViewHolder(binding.root), MultiViewHolder {
    override fun bind(item: MultiItemModel) {
        binding.headerText.text = item.headerText
    }
}