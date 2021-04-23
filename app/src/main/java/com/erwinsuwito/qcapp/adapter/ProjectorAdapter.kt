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
import com.erwinsuwito.qcapp.model.Classroom
import com.erwinsuwito.qcapp.model.MoreItem
import com.erwinsuwito.qcapp.model.Projector
import com.google.api.ProjectProperties
import kotlinx.android.synthetic.main.class_list_item.view.*

class ProjectorAdapter(private val context: Context, private val dataset: List<Projector>, private val onClick: (Projector) -> Unit)
    : ListAdapter<Projector, ProjectorAdapter.ItemViewHolder>(projectorItemDiffCallback)
{
    class ItemViewHolder(private val view: View, val onClick: (Projector) -> Unit) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.class_name)
        private var currentItem: Projector? = null

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: Projector)
        {
            currentItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.class_list_item, parent, false)

        return ItemViewHolder(adapterLayout, onClick)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.projectorId
        holder.bind(item)
    }
}

object projectorItemDiffCallback : DiffUtil.ItemCallback<Projector>() {
    override fun areItemsTheSame(oldItem: Projector, newItem: Projector): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Projector, newItem: Projector): Boolean {
        return oldItem.projectorId == newItem.projectorId
    }
}