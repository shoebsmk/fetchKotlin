package com.example.fetchkotlin

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(val itemArrayList: ArrayList<Item>, val mainActivity: MainActivity) :
    RecyclerView.Adapter<ItemsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_entry, parent, false)
        view.setOnClickListener(mainActivity)
        return ItemsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {

        //Mod this later
        val color1 = Color.parseColor("#edbf33") // Yellow
        val color2 = Color.parseColor("#27aeef") // Blue
        val color3 = Color.parseColor("#87bc45") // Green
        val color4 = Color.parseColor("#df8879") // Pink
        val color5 = Color.parseColor("#b3bfd1") // Gray
        val item = itemArrayList[position]
        holder.itemsId.text  = item.id;
        holder.itemListId.text = item.listId
        holder.itemName.text = item.name
        val listId = item.listId
        when (listId) {
            "1" -> holder.constraintLayout.setBackgroundColor(color1)
            "2" -> holder.constraintLayout.setBackgroundColor(color2)
            "3" -> holder.constraintLayout.setBackgroundColor(color3)
            "4" -> holder.constraintLayout.setBackgroundColor(color4)
            else -> holder.constraintLayout.setBackgroundColor(color5)
        }
        //holder.getConstraintLayout().setBackgroundColor(color);
    }

    override fun getItemCount(): Int {
        return itemArrayList.size
    }
}


