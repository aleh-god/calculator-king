package by.godevelopment.kingcalculator.presentation.partypresentation.partyinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.databinding.ItemPartyInfoListBinding
import by.godevelopment.kingcalculator.domain.partiesdomain.models.PartyInfoItemModel

class PartyInfoAdapter(
    private val items: List<PartyInfoItemModel>
) : RecyclerView.Adapter<PartyInfoAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPartyInfoListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PartyInfoItemModel) {
            binding.apply {
                gameName.text = root.resources.getString(item.gameType.res)
                gameCollumOne.text = item.oneGameScore?.toString()
                gameCollumTwo.text = item.twoGameScore?.toString()
                gameCollumThree.text = item.threeGameScore?.toString()
                gameCollumFour.text = item.fourGameScore?.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPartyInfoListBinding.inflate(
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
