package com.erwinsuwito.qcapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.model.MoreItem
import com.erwinsuwito.qcapp.ui.more.MoreFragment

class MoreItemsAdapter(private val context: MoreFragment, private val dataset: List<MoreItem>) : RecyclerView.Adapter<MoreItemsAdapter.ItemViewHolder>() {
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.actionTextView)
        val imageView: ImageView = view.findViewById(R.id.actionIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.more_actions_list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = context.resources.getString(item.actionId)
        holder.imageView.setImageDrawable(context.resources.getDrawable(item.imgId))
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}