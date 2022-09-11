package by.godevelopment.kingcalculator.presentation.partypresentation.partycard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.EMPTY_STRING
import by.godevelopment.kingcalculator.databinding.ItemGamesTableBinding
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.partiesdomain.models.GamesTableItemModel
import com.google.android.material.textview.MaterialTextView

class PartyCardAdapter(
    private val onClick: (GameType) -> Unit
) : RecyclerView.Adapter<PartyCardAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<GamesTableItemModel>() {
        override fun areItemsTheSame(oldItem: GamesTableItemModel, newItem: GamesTableItemModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GamesTableItemModel, newItem: GamesTableItemModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var items: List<GamesTableItemModel>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    inner class ViewHolder(private val binding: ItemGamesTableBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GamesTableItemModel) {
            binding.apply {
                gameName.text = root.resources.getString(item.gameType.res)

                when (item.openedColumnIndex) {
                    0 -> {
                        if (item.isFinishedOneGame) setCompleteFrame(gameCollumOne)
                        else setOpenFrame(gameCollumOne, item.gameType)
                        if (item.isFinishedTwoGame) setCompleteFrame(gameCollumTwo)
                        else setCloseFrame(gameCollumTwo)
                        if (item.isFinishedThreeGame) setCompleteFrame(gameCollumThree)
                        else setCloseFrame(gameCollumThree)
                        if (item.isFinishedFourGame) setCompleteFrame(gameCollumFour)
                        else setCloseFrame(gameCollumFour)
                    }
                    1 -> {
                        if (item.isFinishedTwoGame) setCompleteFrame(gameCollumTwo)
                        else setOpenFrame(gameCollumTwo, item.gameType)
                        if (item.isFinishedOneGame) setCompleteFrame(gameCollumOne)
                        else setCloseFrame(gameCollumOne)
                        if (item.isFinishedThreeGame) setCompleteFrame(gameCollumThree)
                        else setCloseFrame(gameCollumThree)
                        if (item.isFinishedFourGame) setCompleteFrame(gameCollumFour)
                        else setCloseFrame(gameCollumFour)
                    }
                    2 -> {
                        if (item.isFinishedThreeGame) setCompleteFrame(gameCollumThree)
                        else setOpenFrame(gameCollumThree, item.gameType)
                        if (item.isFinishedOneGame) setCompleteFrame(gameCollumOne)
                        else setCloseFrame(gameCollumOne)
                        if (item.isFinishedTwoGame) setCompleteFrame(gameCollumTwo)
                        else setCloseFrame(gameCollumTwo)
                        if (item.isFinishedFourGame) setCompleteFrame(gameCollumFour)
                        else setCloseFrame(gameCollumFour)
                    }
                    3 -> {
                        if (item.isFinishedFourGame) setCompleteFrame(gameCollumFour)
                        else setOpenFrame(gameCollumFour, item.gameType)
                        if (item.isFinishedOneGame) setCompleteFrame(gameCollumOne)
                        else setCloseFrame(gameCollumOne)
                        if (item.isFinishedTwoGame) setCompleteFrame(gameCollumTwo)
                        else setCloseFrame(gameCollumTwo)
                        if (item.isFinishedThreeGame) setCompleteFrame(gameCollumThree)
                        else setCloseFrame(gameCollumThree)
                    }
                }
            }
        }

        private fun setCompleteFrame(view: MaterialTextView) {
            view.text = "X"
            view.setBackgroundResource(R.drawable.frame_complete)
            view.setOnClickListener { }
        }
        private fun setOpenFrame(view: MaterialTextView, gameType: GameType) {
            view.setBackgroundResource(R.drawable.frame_open)
            view.text = EMPTY_STRING
            view.setOnClickListener { onClick.invoke(gameType) }
        }
        private fun setCloseFrame(view: MaterialTextView) {
            view.setBackgroundResource(R.drawable.frame_close)
            view.text = EMPTY_STRING
            view.setOnClickListener { }
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
