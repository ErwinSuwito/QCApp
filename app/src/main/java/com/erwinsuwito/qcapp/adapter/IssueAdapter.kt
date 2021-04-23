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
import com.erwinsuwito.qcapp.model.Issue
import com.erwinsuwito.qcapp.model.MoreItem
import kotlinx.android.synthetic.main.class_list_item.view.*

class IssueAdapter(private val context: Context, private val dataset: List<Issue>, private val onClick: (Issue) -> Unit)
    : ListAdapter<Issue, IssueAdapter.ItemViewHolder>(issueItemDiffCallback)
{
    class ItemViewHolder(private val view: View, val onClick: (Issue) -> Unit) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.class_name)
        private var currentItem: Issue? = null

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: Issue)
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
        holder.textView.text = item.issueId
        holder.bind(item)
    }
}

object issueItemDiffCallback : DiffUtil.ItemCallback<Issue>() {
    override fun areItemsTheSame(oldItem: Issue, newItem: Issue): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Issue, newItem: Issue): Boolean {
        return oldItem.issueId == newItem.issueId
    }
}