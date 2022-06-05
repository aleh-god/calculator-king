package by.godevelopment.kingcalculator.presentation.playerslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.databinding.ItemPlayersListBinding
import by.godevelopment.kingcalculator.domain.models.ItemPlayerModel

class PlayersAdapter(
    private val onClick: (Long) -> Unit
) : RecyclerView.Adapter<PlayersAdapter.ItemViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ItemPlayerModel>() {
        override fun areItemsTheSame(oldItem: ItemPlayerModel, newItem: ItemPlayerModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ItemPlayerModel, newItem: ItemPlayerModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var itemList: List<ItemPlayerModel>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    inner class ItemViewHolder(private val binding: ItemPlayersListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemPlayerModel: ItemPlayerModel) {
            binding.apply {
                playerName.text = itemPlayerModel.name
                playerEmail.text = itemPlayerModel.email
                root.setOnClickListener {
                    onClick.invoke(itemPlayerModel.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemPlayersListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}