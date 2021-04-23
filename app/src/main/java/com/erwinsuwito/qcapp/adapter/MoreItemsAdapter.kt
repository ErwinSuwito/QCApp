package com.erwinsuwito.qcapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.model.MoreItem
import com.erwinsuwito.qcapp.ui.more.MoreFragment

class MoreItemsAdapter(private val context: MoreFragment, private val dataset: List<MoreItem>, private val onClick: (MoreItem) -> Unit ) : ListAdapter<MoreItem, MoreItemsAdapter.ItemViewHolder>(moreItemItemDiffCallback) {
    class ItemViewHolder(private val view: View, val onClick: (MoreItem) -> Unit) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.actionTextView)
        val imageView: ImageView = view.findViewById(R.id.actionIcon)
        private var currentItem: MoreItem? = null

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }

        fun bind (item: MoreItem)
        {
            currentItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.more_actions_list_item, parent, false)

        return ItemViewHolder(adapterLayout, onClick)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = context.resources.getString(item.actionId)
        holder.imageView.setImageDrawable(context.resources.getDrawable(item.imgId))
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}

private object moreItemItemDiffCallback : DiffUtil.ItemCallback<MoreItem>() {
    override fun areItemsTheSame(oldItem: MoreItem, newItem: MoreItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MoreItem, newItem: MoreItem): Boolean {
        return oldItem.actionId == newItem.actionId
    }
}