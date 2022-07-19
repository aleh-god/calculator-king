package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.viewholdes

import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.databinding.FooterGameAddFormBinding
import by.godevelopment.kingcalculator.domain.gamesdomain.models.FooterItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel

class FooterViewHolder(
    private val binding: FooterGameAddFormBinding
) : RecyclerView.ViewHolder(binding.root), MultiViewHolder {
    override fun bind(item: MultiItemModel) {
        when(item) {
            is FooterItemModel -> {
                val footerText = binding.root.context.getString(R.string.rv_footer_text_total_score)
                binding.footerText.text = footerText + item.totalGameScore.toString()
            }
            else -> {}
        }
    }
}