package by.godevelopment.kingcalculator.presentation.partypresentation.partycard

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.EMPTY_STRING
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.databinding.ItemGamesTableBinding
import by.godevelopment.kingcalculator.domain.commons.models.TestItem
import com.google.android.material.textview.MaterialTextView

class PartyCardAdapter(
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<PartyCardAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<TestItem>() {
        override fun areItemsTheSame(oldItem: TestItem, newItem: TestItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TestItem, newItem: TestItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var items: List<TestItem>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    inner class ViewHolder(private val binding: ItemGamesTableBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(testItem: TestItem) {
            Log.i(TAG, "bind: ${testItem.isFinishedOneGame} - ${testItem.isFinishedTwoGame} - ${testItem.isFinishedThreeGame} - ${testItem.isFinishedFourGame}")
            binding.apply {
                gameName.text = testItem.gameTypeName

                when (testItem.openedColumnNumber) {
                    1 -> {
                        if (testItem.isFinishedOneGame) setCompleteFrame(gameCollumOne)
                        else {
                            setOpenFrame(gameCollumOne, testItem.gameTypeName)
                            if (testItem.isFinishedTwoGame) setCompleteFrame(gameCollumTwo)
                            else setCloseFrame(gameCollumTwo)
                            if (testItem.isFinishedThreeGame) setCompleteFrame(gameCollumThree)
                            else setCloseFrame(gameCollumThree)
                            if (testItem.isFinishedFourGame) setCompleteFrame(gameCollumFour)
                            else setCloseFrame(gameCollumFour)
                        }
                    }
                    2 -> {
                        if (testItem.isFinishedTwoGame) { setCompleteFrame(gameCollumTwo) }
                        else {
                            setOpenFrame(gameCollumTwo, testItem.gameTypeName)
                            if (testItem.isFinishedOneGame) setCompleteFrame(gameCollumOne)
                            else setCloseFrame(gameCollumOne)
                            if (testItem.isFinishedThreeGame) setCompleteFrame(gameCollumThree)
                            else setCloseFrame(gameCollumThree)
                            if (testItem.isFinishedFourGame) setCompleteFrame(gameCollumFour)
                            else setCloseFrame(gameCollumFour)
                        }
                    }
                    3 -> {
                        if (testItem.isFinishedThreeGame) { setCompleteFrame(gameCollumThree) }
                        else {
                            setOpenFrame(gameCollumThree, testItem.gameTypeName)
                            if (testItem.isFinishedOneGame) setCompleteFrame(gameCollumOne)
                            else setCloseFrame(gameCollumOne)
                            if (testItem.isFinishedTwoGame) setCompleteFrame(gameCollumTwo)
                            else setCloseFrame(gameCollumTwo)
                            if (testItem.isFinishedFourGame) setCompleteFrame(gameCollumFour)
                            else setCloseFrame(gameCollumFour)
                        }
                    }
                    4 -> {
                        if (testItem.isFinishedFourGame) { setCompleteFrame(gameCollumFour) }
                        else {
                            setOpenFrame(gameCollumFour, testItem.gameTypeName)
                            if (testItem.isFinishedOneGame) setCompleteFrame(gameCollumOne)
                            else setCloseFrame(gameCollumOne)
                            if (testItem.isFinishedTwoGame) setCompleteFrame(gameCollumTwo)
                            else setCloseFrame(gameCollumTwo)
                            if (testItem.isFinishedThreeGame) setCompleteFrame(gameCollumThree)
                            else setCloseFrame(gameCollumThree)
                        }
                    }
                }
            }
        }

        private fun setCompleteFrame(view: MaterialTextView) {
            view.text = "X"
            view.setBackgroundResource(R.drawable.frame_complete)
        }
        private fun setOpenFrame(view: MaterialTextView, gameTypeName: String) {
            view.setOnClickListener { onClick.invoke(gameTypeName) }
            view.setBackgroundResource(R.drawable.frame_open)
            view.text = EMPTY_STRING
        }
        private fun setCloseFrame(view: MaterialTextView) {
            view.setBackgroundResource(R.drawable.frame_close)
            view.text = EMPTY_STRING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGamesTableBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}