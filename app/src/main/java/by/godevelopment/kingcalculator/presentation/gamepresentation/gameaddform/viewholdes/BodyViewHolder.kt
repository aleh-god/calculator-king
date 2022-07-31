package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.viewholdes

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.databinding.ItemGameAddFormBinding
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel

class BodyViewHolder(
    private val binding: ItemGameAddFormBinding,
    private val onClickDec: (Int) -> Unit,
    private val onClickInc: (Int) -> Unit,
    private val onClickEdit: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root), MultiViewHolder {

    override fun bind(item: MultiItemModel, position: Int) {
        binding.apply {
//            Log.i(TAG, "BodyViewHolder bind: $position \n item = $item")
            gameName.text = root.resources.getString(item.gameType.res)
            gameTricks.text = root.resources.getString(R.string.rv_body_text_tricks, item.tricks)

            // TODO("Impl color indicator")
            if(item.hasError) { Log.i(TAG, "BodyViewHolder item.hasError: $position") }
            else { Log.i(TAG, "BodyViewHolder !item.hasError: $position") }

            gameScore.text =
                root.resources.getString(R.string.rv_body_text_score, item.score)
            inputTricksCount.text = item.tricks.toString()

            inputTricksCount.setOnClickListener {
                onClickEdit.invoke(item.rowId)
            }
            buttonDecreaseTricks.setOnClickListener {
                onClickDec.invoke(item.rowId)
            }
            buttonIncreaseTricks.setOnClickListener {
                onClickInc.invoke(item.rowId)
            }
        }
    }
}
