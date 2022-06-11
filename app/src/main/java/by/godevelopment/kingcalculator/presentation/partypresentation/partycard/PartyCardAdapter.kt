package by.godevelopment.kingcalculator.presentation.partypresentation.partycard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.databinding.ItemGamesTableBinding
import by.godevelopment.kingcalculator.domain.commons.models.TestItem

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
            binding.gameName.text = testItem.name
            if (testItem.isFinishedOneGame) binding.gameCollumOne.text = "X"
            if (testItem.isFinishedTwoGame) binding.gameCollumTwo.text = "X"
            if (testItem.isFinishedThreeGame) binding.gameCollumThree.text = "X"
            if (testItem.isFinishedFourGame) binding.gameCollumFour.text = "X"
            binding.root.setOnClickListener {
                onClick.invoke(testItem.name)
            }
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