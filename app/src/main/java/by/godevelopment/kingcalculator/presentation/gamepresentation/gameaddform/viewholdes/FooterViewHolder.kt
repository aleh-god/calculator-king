package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.viewholdes

import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.databinding.FooterGameAddFormBinding
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel

class FooterViewHolder(
    private val binding: FooterGameAddFormBinding
) : RecyclerView.ViewHolder(binding.root), MultiViewHolder {
    override fun bind(item: MultiItemModel) {
        binding.footerText.text = item.footerText
    }
}