package by.godevelopment.kingcalculator.presentation.playerpresentation.playerslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.databinding.ItemPlayersListBinding
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel

class PlayersAdapter(
    private val onClickItem: (Long) -> Unit,
    private val onClickImage: (Long) -> Unit
) : RecyclerView.Adapter<PlayersAdapter.ItemViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<PlayerModel>() {
        override fun areItemsTheSame(oldItem: PlayerModel, newItem: PlayerModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PlayerModel, newItem: PlayerModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var itemList: List<PlayerModel>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    inner class ItemViewHolder(private val binding: ItemPlayersListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemPlayerModel: PlayerModel) {
            binding.apply {
                playerName.text = itemPlayerModel.name
                playerEmail.text = itemPlayerModel.email
                root.setOnClickListener {
                    onClickItem.invoke(itemPlayerModel.id)
                }
                playerStat.setOnClickListener {
                    onClickImage.invoke(itemPlayerModel.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemPlayersListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}
