package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel
import by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.viewholdes.MultiViewHolder
import by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.viewholdes.ViewHolderFactory

class MultiAdapter(
    onClickDec: (Int) -> Unit,
    onClickInc: (Int) -> Unit,
    onClickEdit: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallBack =
        object : DiffUtil.ItemCallback<MultiItemModel>() {

            override fun areItemsTheSame(
                oldItem: MultiItemModel,
                newItem: MultiItemModel
            ): Boolean = oldItem.rowId == newItem.rowId

            override fun areContentsTheSame(
                oldItem: MultiItemModel,
                newItem: MultiItemModel
            ): Boolean = oldItem == newItem

            override fun getChangePayload(oldItem: MultiItemModel, newItem: MultiItemModel): Any? {
                if (oldItem.rowId == newItem.rowId) {
                    if (oldItem == newItem) {
                        super.getChangePayload(oldItem, newItem)
                    } else {
                        return Bundle().apply {
                            putInt(ARG_SCORE, newItem.score)
                            putInt(ARG_TRICKS, newItem.tricks)
                        }
                    }
                }
                return super.getChangePayload(oldItem, newItem)
            }
        }

    private val differ = AsyncListDiffer(this, diffCallBack)

    var multiList: List<MultiItemModel>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    private val viewHolderFactory: ViewHolderFactory = ViewHolderFactory(
        onClickDec = onClickDec,
        onClickInc = onClickInc,
        onClickEdit = onClickEdit
    )

    override fun getItemViewType(position: Int): Int = multiList[position].itemViewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        viewHolderFactory.buildHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindMultiViewHolder(holder = holder, position = position)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty() || payloads[0] !is Bundle) {
            onBindMultiViewHolder(holder = holder, position = position)
        } else {
            val bundle = payloads[0] as Bundle
            val multiHolder = holder as MultiViewHolder
            multiHolder.update(bundle)
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int = multiList.size

    private fun onBindMultiViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = multiList[position]
        val multiHolder = holder as MultiViewHolder
        multiHolder.bind(item, position)
    }

    companion object {
        const val ARG_SCORE = "arg.score"
        const val ARG_TRICKS = "arg.tricks"
    }
}
