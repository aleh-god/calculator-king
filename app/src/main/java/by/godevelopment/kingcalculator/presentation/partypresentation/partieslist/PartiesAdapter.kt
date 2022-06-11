package by.godevelopment.kingcalculator.presentation.partypresentation.partieslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.databinding.ItemPartiesListBinding
import by.godevelopment.kingcalculator.domain.partiesdomain.models.ItemPartyModel

class PartiesAdapter(
    private val onClick: (Long) -> Unit
) : RecyclerView.Adapter<PartiesAdapter.ItemViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ItemPartyModel>() {
        override fun areItemsTheSame(oldItem: ItemPartyModel, newItem: ItemPartyModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ItemPartyModel, newItem: ItemPartyModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var items: List<ItemPartyModel>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    inner class ItemViewHolder(private val binding: ItemPartiesListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemPartyModel: ItemPartyModel) {
            binding.apply {
                partyName.text = itemPartyModel.partyName
                partyGamesCount.text = itemPartyModel.partyGamesCount
                playerOneName.text = itemPartyModel.player_one_name
                playerOneScore.text = itemPartyModel.player_one_score
                playerTwoName.text = itemPartyModel.player_two_name
                playerTwoScore.text = itemPartyModel.player_two_score
                playerThreeName.text = itemPartyModel.player_three_name
                playerThreeScore.text = itemPartyModel.player_three_score
                playerFourName.text = itemPartyModel.player_four_name
                playerFourScore.text = itemPartyModel.player_four_score
                root.setOnClickListener {
                    onClick.invoke(itemPartyModel.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemPartiesListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
