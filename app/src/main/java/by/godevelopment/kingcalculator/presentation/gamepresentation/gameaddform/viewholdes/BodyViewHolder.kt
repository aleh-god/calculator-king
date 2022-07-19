package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.viewholdes

import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.databinding.ItemGameAddFormBinding
import by.godevelopment.kingcalculator.domain.gamesdomain.models.BodyItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel

class BodyViewHolder(
    private val binding: ItemGameAddFormBinding
) : RecyclerView.ViewHolder(binding.root), MultiViewHolder {
    override fun bind(item: MultiItemModel) {
        when(item) {
            is BodyItemModel -> {
                binding.apply {
                    gameName.text = root.resources.getString(item.gameTypesName)
                    gameTotalScore.text = item.totalScore.toString()
                    inputTricksCount.setText(item.totalTricks.toString())
                    buttonDecreaseTricks.setOnClickListener {
                        inputTricksCount.text.toString().toIntOrNull()?.let { old ->
                            val text = (old - 1).toString()
                            inputTricksCount.setText(text)
                        }
                    }
                    buttonIncreaseTricks.setOnClickListener {
                        inputTricksCount.text.toString().toIntOrNull()?.let { old ->
                            val text = (old + 1).toString()
                            inputTricksCount.setText(text)
                        }
                    }
                }
            }
            else -> {}
        }
    }
}