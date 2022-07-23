package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.viewholdes

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.databinding.HeaderGameAddFormBinding
import by.godevelopment.kingcalculator.domain.gamesdomain.models.HeaderItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel

class HeaderViewHolder(
    private val binding: HeaderGameAddFormBinding
) : RecyclerView.ViewHolder(binding.root), MultiViewHolder {
    override fun bind(item: MultiItemModel, position: Int) { // TODO("change item type")
        when(item) {
            is HeaderItemModel -> {
                Log.i(TAG, "HeaderItemModel position $position bind: ${item.rowId}")
                binding.headerText.text = binding.root.resources.run {
                    StringBuilder()
                        .append(getString(item.playerNumberRes))
                        .append(getString(R.string.rv_header_text_divider))
                        .append(item.player.name)
                        .toString()
                }
            }
            else -> {
                Log.i(TAG, "HeaderItemModel bind: else")
            }
        }
    }
}