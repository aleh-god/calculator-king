package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.viewholdes

import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.databinding.ItemGameAddFormBinding
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel

class BodyViewHolder(
    private val binding: ItemGameAddFormBinding
) : RecyclerView.ViewHolder(binding.root), MultiViewHolder {
    override fun bind(item: MultiItemModel) {
        binding.gameName.text = item.gameTypesName
        binding.gameTotalScore.text = item.totalScore.toString()
        binding.inputTricksCount.setText(item.totalTricks.toString())
    }
}