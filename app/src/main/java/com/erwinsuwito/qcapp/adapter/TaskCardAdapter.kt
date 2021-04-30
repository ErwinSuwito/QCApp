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
import com.erwinsuwito.qcapp.model.Task
import com.microsoft.fluentui.persona.AvatarView
import kotlinx.android.synthetic.main.class_list_item.view.*
import org.w3c.dom.Text

class TaskCardAdapter(private val context: Context, private val dataset: List<Task>, private val onClick: (Task) -> Unit)
    : ListAdapter<Task, TaskCardAdapter.ItemViewHolder>(taskItemDiffCallback)
{
    class ItemViewHolder(private val view: View, val onClick: (Task) -> Unit) : RecyclerView.ViewHolder(view) {
        val taskTitle: TextView = view.findViewById(R.id.task_title)
        val taskDesc: TextView = view.findViewById(R.id.task_desc)
        val taskAddedDate: TextView = view.findViewById(R.id.task_added_date)
        val avatarAuthor: AvatarView = view.findViewById(R.id.avatar_author)
        val taskAuthor: TextView = view.findViewById(R.id.task_author)

        private var currentItem: Task? = null

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: Task)
        {
            currentItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.tasks_list_view_item_template, parent, false)

        return ItemViewHolder(adapterLayout, onClick)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.taskTitle.text = item.taskTitle
        holder.taskDesc.text = item.problem
        holder.taskAddedDate.text = item.openedOn.toString()
        holder.taskAuthor.text = item.creatorName
        holder.avatarAuthor.name = item.creatorName

        holder.bind(item)
    }
}

object taskItemDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.issueId == newItem.issueId
    }
}