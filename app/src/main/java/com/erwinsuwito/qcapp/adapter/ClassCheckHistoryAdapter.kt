package com.erwinsuwito.qcapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.erwinsuwito.qcapp.App
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.model.ClassCheck
import com.erwinsuwito.qcapp.model.Classroom
import com.erwinsuwito.qcapp.model.MoreItem
import kotlinx.android.synthetic.main.class_list_item.view.*

class ClassCheckHistoryAdapter(private val context: Context, private val dataset: List<ClassCheck>, private val onClick: (ClassCheck) -> Unit)
    : ListAdapter<ClassCheck, ClassCheckHistoryAdapter.ItemViewHolder>(classCheckItemDiffCallback)
{
    class ItemViewHolder(private val view: View, val onClick: (ClassCheck) -> Unit) : RecyclerView.ViewHolder(view) {
        val checkerName: TextView = view.findViewById(R.id.checker_name)
        val checkStatus: TextView = view.findViewById(R.id.check_status)
        val checkedOn: TextView = view.findViewById(R.id.checked_on)

        private var currentItem: ClassCheck? = null

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: ClassCheck)
        {
            currentItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.class_check_item_template, parent, false)

        return ItemViewHolder(adapterLayout, onClick)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.checkerName.text = item.checkedByName
        holder.checkedOn.text = item.checkedOn.toDate().toLocaleString()

        if (item.isEverythingOk)
        {
            holder.checkStatus.text = App.context?.getString(R.string.no_problems_found)
        }
        else
        {
            holder.checkStatus.text = App.context?.getString(R.string.problems_found)
        }
        holder.bind(item)
    }
}

object classCheckItemDiffCallback : DiffUtil.ItemCallback<ClassCheck>() {
    override fun areItemsTheSame(oldItem: ClassCheck, newItem: ClassCheck): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ClassCheck, newItem: ClassCheck): Boolean {
        return oldItem.checkId == newItem.checkId
    }
}