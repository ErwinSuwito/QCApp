package com.erwinsuwito.qcapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.model.Classroom
import com.erwinsuwito.qcapp.model.Issue
import com.erwinsuwito.qcapp.model.MoreItem
import kotlinx.android.synthetic.main.class_list_item.view.*
import java.time.format.DateTimeFormatter

class IssueCardAdapter(private val context: Context, private val dataset: List<Issue>, private val showClassName: Boolean, private val onClick: (Issue) -> Unit)
    : ListAdapter<Issue, IssueCardAdapter.ItemViewHolder>(issueItemDiffCallback)
{
    class ItemViewHolder(private val view: View, val onClick: (Issue) -> Unit) : RecyclerView.ViewHolder(view) {
        val className: TextView = view.findViewById(R.id.issue_class_name)
        val problem: TextView = view.findViewById(R.id.issue_desc)
        val submittedOn: TextView = view.findViewById(R.id.issue_added_date)
        val issueSolvedPanel: LinearLayout = view.findViewById(R.id.issueSolvedPanel)
        val addedOnText: TextView = view.findViewById(R.id.addedOnText)

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
                .inflate(R.layout.issues_card_item_template, parent, false)

        return ItemViewHolder(adapterLayout, onClick)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")

        val item = dataset[position]
        holder.className.text = item.classroom
        holder.problem.text = item.problem

        if (item.isOpen)
        {
            holder.addedOnText.text = context.resources.getString(R.string.added_on)
            holder.submittedOn.text = item.openedOn.format(formatter)
        }
        else
        {
            holder.submittedOn.text = item.closedOn.format(formatter)
            holder.addedOnText.text = context.resources.getString(R.string.solved_on)
            holder.issueSolvedPanel.visibility = View.VISIBLE
        }

        if (!showClassName)
        {
            holder.className.visibility = View.GONE
        }

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