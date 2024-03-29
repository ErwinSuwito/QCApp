package com.erwinsuwito.qcapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.model.Steps
import com.microsoft.fluentui.persona.AvatarView

class StepsAdapter(private val context: Context, private val dataset: List<Steps>, private val onClick: (Steps) -> Unit)
    : ListAdapter<Steps, StepsAdapter.ItemViewHolder>(stepsItemDiffCallback)
{
    class ItemViewHolder(private val view: View, val onClick: (Steps) -> Unit) : RecyclerView.ViewHolder(view) {
        val authorName: TextView = view.findViewById(R.id.reply_author)
        val authorAvatar: AvatarView = view.findViewById(R.id.reply_avatar)
        val replyContent: TextView = view.findViewById(R.id.reply_replyContent)
        val addedDate: TextView = view.findViewById(R.id.reply_dateAdded)

        private var currentItem: Steps? = null

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: Steps)
        {
            currentItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.troubleshoot_step_list_item, parent, false)

        return ItemViewHolder(adapterLayout, onClick)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.authorAvatar.name = item.authorName
        holder.authorName.text = item.authorName
        holder.replyContent.text = item.content
        holder.addedDate.text = item.addedOn.toDate().toLocaleString()

        holder.bind(item)
    }
}

object stepsItemDiffCallback : DiffUtil.ItemCallback<Steps>() {
    override fun areItemsTheSame(oldItem: Steps, newItem: Steps): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Steps, newItem: Steps): Boolean {
        return oldItem.stepId == newItem.stepId
    }
}