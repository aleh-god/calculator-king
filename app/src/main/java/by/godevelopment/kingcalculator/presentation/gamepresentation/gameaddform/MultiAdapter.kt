package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform

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

            override fun areItemsTheSame(oldItem: MultiItemModel, newItem: MultiItemModel): Boolean {
                return oldItem.rowId == newItem.rowId
            }

            override fun areContentsTheSame(oldItem: MultiItemModel, newItem: MultiItemModel): Boolean {
                return oldItem == newItem
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
        val item = multiList[position]
        val multiHolder = holder as MultiViewHolder
        multiHolder.bind(item, position)
    }

    override fun getItemCount(): Int = multiList.size
}
