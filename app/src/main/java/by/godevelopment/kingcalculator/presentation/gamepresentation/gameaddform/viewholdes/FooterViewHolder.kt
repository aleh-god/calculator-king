package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.viewholdes

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.databinding.FooterGameAddFormBinding
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel

class FooterViewHolder(
    private val binding: FooterGameAddFormBinding
) : RecyclerView.ViewHolder(binding.root), MultiViewHolder {
    override fun bind(item: MultiItemModel, position: Int) {
        Log.i(TAG, "bind: ${item.playerNumber.id} = ${item.totalPlayerScore}")
        binding.footerText.text = binding.root.resources.getString(
            R.string.rv_footer_text_total_score,
            item.totalPlayerScore
        )
    }
}