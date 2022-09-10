package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.viewholdes

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.databinding.ItemGameAddFormBinding
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel
import by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.MultiAdapter.Companion.ARG_SCORE
import by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.MultiAdapter.Companion.ARG_TRICKS

class BodyViewHolder(
    private val binding: ItemGameAddFormBinding,
    private val onClickDec: (Int) -> Unit,
    private val onClickInc: (Int) -> Unit,
    private val onClickEdit: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root), MultiViewHolder {

    override fun bind(item: MultiItemModel, position: Int) {
        Log.i(TAG, "BodyViewHolder bind: $position = $item")
        binding.apply {
            gameName.text = root.resources.getString(item.gameType.res)
            gameTricks.text = root.resources.getString(R.string.rv_body_text_tricks, item.tricks)
            gameScore.text = root.resources.getString(R.string.rv_body_text_score, item.score)
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

            if(item.hasError) {
                gameScore.text = root.resources.getString(R.string.rv_body_text_error)
            }
            else {
                gameScore.text = root.resources.getString(R.string.rv_body_text_score, item.score)
            }
        }
    }

    override fun update(bundle: Bundle) {
        val tricks = bundle.getInt(ARG_TRICKS)
        val score = bundle.getInt(ARG_SCORE)
        Log.i(TAG, "BodyViewHolder update: $tricks and $score")
        binding.apply {
            gameTricks.text = root.resources.getString(R.string.rv_body_text_tricks, tricks)
            gameScore.text = root.resources.getString(R.string.rv_body_text_score, score)
            inputTricksCount.text = tricks.toString()
        }
    }
}
