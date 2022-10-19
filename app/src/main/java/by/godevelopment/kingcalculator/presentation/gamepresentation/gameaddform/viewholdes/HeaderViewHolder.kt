package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.viewholdes

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.databinding.HeaderGameAddFormBinding
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel

class HeaderViewHolder(
    private val binding: HeaderGameAddFormBinding
) : RecyclerView.ViewHolder(binding.root), MultiViewHolder {

    override fun bind(item: MultiItemModel, position: Int) {
        binding.headerText.text = binding.root.resources.run {
            StringBuilder()
                .append(getString(item.playerNumber.res))
                .append(getString(R.string.rv_header_text_divider))
                .append(item.player.name)
                .toString()
        }
    }

    override fun update(bundle: Bundle) {
    }
}
