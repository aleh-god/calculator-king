package by.godevelopment.kingcalculator.presentation.playerpresentation.playerinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.databinding.ItemPlayerInfoListBinding
import by.godevelopment.kingcalculator.domain.playersdomain.models.ItemPlayerInfoModel

class InfoAdapter(
    private val itemList: List<ItemPlayerInfoModel>
) : RecyclerView.Adapter<InfoAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemPlayerInfoListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemInfoModel: ItemPlayerInfoModel) {
            binding.apply {
                infoType.text = root.resources.getString(itemInfoModel.type)
                infoValue.text = itemInfoModel.value
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemPlayerInfoListBinding.inflate(
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
