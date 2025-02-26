package com.pjiang.fetch_coding_exercise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pjiang.fetch_coding_exercise.R
import com.pjiang.fetch_coding_exercise.databinding.ItemDataBinding
import com.pjiang.fetch_coding_exercise.entities.HiringEntity

class HiringGroupAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var originalGroupMap: Map<Int, List<HiringEntity>> = emptyMap()

    private val items = mutableListOf<ListItem>()

    private val groupExpandStates = mutableMapOf<Int, Boolean>()

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    fun setData(groupedData: Map<Int, List<HiringEntity>>) {
        originalGroupMap = groupedData

        groupedData.keys.forEach { listId ->
            if (!groupExpandStates.containsKey(listId)) {
                groupExpandStates[listId] = true
            }
        }

        updateListItems()
    }

    private fun generateNewList(): List<ListItem> {
        val newList = mutableListOf<ListItem>()

        originalGroupMap.forEach { (listId, groupItems) ->
            val isExpanded = groupExpandStates[listId] ?: true
            newList.add(ListItem.HeaderItem(listId, isExpanded))

            if (isExpanded) {
                groupItems.forEach { item ->
                    newList.add(ListItem.HiringItem(item))
                }
            }
        }

        return newList
    }

    private fun updateListItems() {
        val newList = generateNewList()

        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = items.size
            override fun getNewListSize() = newList.size

            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
                return items[oldPos].getItemId() == newList[newPos].getItemId()
            }

            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
                val oldItem = items[oldPos]
                val newItem = newList[newPos]

                return when {
                    oldItem is ListItem.HeaderItem && newItem is ListItem.HeaderItem ->
                        oldItem.listId == newItem.listId && oldItem.isExpanded == newItem.isExpanded
                    oldItem is ListItem.HiringItem && newItem is ListItem.HiringItem ->
                        oldItem.hiringData == newItem.hiringData
                    else -> false
                }
            }
        }

        //calculate diff
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(newList)

        //apply data change
        diffResult.dispatchUpdatesTo(this)
    }

    fun toggleGroup(position: Int) {
        if (position < 0 || position >= items.size) return

        val item = items[position]
        if (item is ListItem.HeaderItem) {
            // 更新展开状态
            val listId = item.listId
            val currentState = groupExpandStates[listId] ?: true
            groupExpandStates[listId] = !currentState

            // 使用 DiffUtil 更新列表
            updateListItems()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ListItem.HeaderItem -> TYPE_HEADER
            is ListItem.HiringItem -> TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_header, parent, false)
                HeaderViewHolder(view)
            }
            TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_data, parent, false)
                ItemViewHolder(view)
            }
            else -> throw IllegalArgumentException("Unknown Item layout")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ListItem.HeaderItem -> (holder as HeaderViewHolder).bind(item, position)
            is ListItem.HiringItem -> (holder as ItemViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvListId: TextView = itemView.findViewById(R.id.tvListId)
        private val tvExpandIcon: TextView = itemView.findViewById(R.id.tvExpandIcon)

        fun bind(header: ListItem.HeaderItem, position: Int) {
            tvListId.text = "List ID: ${header.listId}"

            if (header.isExpanded) {
                tvExpandIcon.setBackgroundResource(android.R.drawable.arrow_down_float)
            } else {
                tvExpandIcon.setBackgroundResource(android.R.drawable.arrow_up_float)
            }


            itemView.setOnClickListener {
                toggleGroup(adapterPosition)
            }
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)

        fun bind(item: ListItem.HiringItem) {
            tvName.text = item.hiringData.name
        }
    }
}

sealed class ListItem {
    data class HeaderItem(val listId: Int, val isExpanded: Boolean = true): ListItem() {
        override fun getItemId(): Any {
            return "header_$listId"
        }
    }

    data class HiringItem(val hiringData: HiringEntity): ListItem() {
        override fun getItemId(): Any {
            return "item_${hiringData.listId}_${hiringData.name}"
        }
    }

    abstract fun getItemId(): Any
}