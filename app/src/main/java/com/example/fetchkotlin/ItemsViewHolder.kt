package com.example.fetchkotlin

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var constraintLayout: ConstraintLayout
    var itemsId: TextView
    var itemListId: TextView
    var itemName: TextView

    init {
        constraintLayout = itemView.findViewById(R.id.item_entry)
        itemsId = itemView.findViewById(R.id.item_id_tv)
        itemListId = itemView.findViewById(R.id.list_id_tv)
        itemName = itemView.findViewById(R.id.name_tv)
    }

    fun getlistId(): TextView {
        return itemListId
    }
}