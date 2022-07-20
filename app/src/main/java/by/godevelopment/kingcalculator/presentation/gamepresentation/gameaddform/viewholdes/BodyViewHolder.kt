package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.viewholdes

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.databinding.ItemGameAddFormBinding
import by.godevelopment.kingcalculator.domain.gamesdomain.models.BodyItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel

class BodyViewHolder(
    private val binding: ItemGameAddFormBinding,
    private val onClickDec: (Int) -> Unit,
    private val onClickInc: (Int) -> Unit,
    private val onChangeEdit: (Int, Int) -> Unit
) : RecyclerView.ViewHolder(binding.root), MultiViewHolder {

    private val myCustomEditTextListener = object : TextWatcher {

        private var position: Int? = null

        fun updatePosition(position: Int) { this.position = position }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.i(TAG, "onTextChanged $position = $p0 ")
            p0.toString().toIntOrNull()?.let { count ->
                position?.let { position ->
                    onChangeEdit.invoke(position, count)
                }
            }
        }

        override fun afterTextChanged(p0: Editable?) {}
    }

    init {
        Log.i(TAG, "BodyItemModel init: $this")
        binding.inputTricksCount.addTextChangedListener(myCustomEditTextListener)

//        binding.inputTricksCount.setOnEditorActionListener { _, actionId, keyEvent ->
//            if (keyEvent != null && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER
//                || actionId == EditorInfo.IME_ACTION_DONE) {
//                binding.inputTricksCount.inputType = InputType.TYPE_NULL
//                binding.inputTricksCount.clearFocus()
//                return@setOnEditorActionListener true
//            }
//            else return@setOnEditorActionListener false
//        }
    }

    override fun bind(item: MultiItemModel) {
        when(item) {
            is BodyItemModel -> {
                binding.apply {
                    Log.i(TAG, "BodyItemModel bind: $item")
                    gameName.text = root.resources.getString(item.gameType.res)
                    gameTotalScore.text = item.totalScore.toString()

                    inputTricksCount.setText(item.totalTricks.toString())
                    myCustomEditTextListener.updatePosition(item.rowId)

//                    inputTricksCount.setText(item.totalTricks.toString())
//                    inputTricksCount.doAfterTextChanged {
//                        Log.i(TAG, "doAfterTextChanged ${item.rowId} = $it ")
//                        it.toString().toIntOrNull()?.let { count ->
//                            onChangeEdit.invoke(item.rowId, count)
//                        }
//                    }
                    buttonDecreaseTricks.setOnClickListener {
                        onClickDec.invoke(item.rowId)
                    }
                    buttonIncreaseTricks.setOnClickListener {
                        onClickInc.invoke(item.rowId)
                    }
                }
            }
            else -> {}
        }
    }
}
