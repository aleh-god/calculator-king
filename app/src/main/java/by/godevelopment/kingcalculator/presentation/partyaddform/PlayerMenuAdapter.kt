package by.godevelopment.kingcalculator.presentation.partyaddform

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.models.PlayerMenuItem

class PlayerMenuAdapter(
    context: Context,
    private val players: List<PlayerMenuItem>
) : ArrayAdapter<PlayerMenuItem>(context, R.layout.item_double_string_array, players) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val player = players[position]
        val itemView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_double_string_array, parent, false)
        val nameView = itemView.findViewById<TextView>(R.id.array_item_name)
        val emailView = itemView.findViewById<TextView>(R.id.array_item_email)
        nameView.text = player.name
        emailView.text = player.email
        return itemView
    }
}