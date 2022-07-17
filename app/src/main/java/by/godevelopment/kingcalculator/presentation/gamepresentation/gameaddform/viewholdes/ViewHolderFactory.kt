package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform.viewholdes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kingcalculator.commons.BODY_ROW_TYPE
import by.godevelopment.kingcalculator.commons.FOOTER_ROW_TYPE
import by.godevelopment.kingcalculator.commons.HEADER_ROW_TYPE
import by.godevelopment.kingcalculator.databinding.FooterGameAddFormBinding
import by.godevelopment.kingcalculator.databinding.HeaderGameAddFormBinding
import by.godevelopment.kingcalculator.databinding.ItemGameAddFormBinding

class ViewHolderFactory {

    fun buildHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType) {
            HEADER_ROW_TYPE -> {
                HeaderViewHolder(
                    HeaderGameAddFormBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            BODY_ROW_TYPE -> {
                BodyViewHolder(
                    ItemGameAddFormBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            FOOTER_ROW_TYPE -> {
                FooterViewHolder(
                    FooterGameAddFormBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalArgumentException()
        }
}