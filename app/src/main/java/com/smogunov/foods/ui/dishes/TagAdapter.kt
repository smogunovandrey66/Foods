package com.smogunov.foods.ui.dishes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.smogunov.domain.global.models.presentation.Tag
import com.smogunov.foods.R
import com.smogunov.foods.databinding.ItemTagBinding


/**
 * Адаптер для тегов
 */
class TagAdapter(private val tags: List<Tag>, private val tagClick: (String) -> Unit) :
    RecyclerView.Adapter<TagAdapter.ItemTagViewHolder>() {

    var selectedPosition: Int = 0
    var lastSelectedPosition: Int = -1

    class ItemTagViewHolder(
        val binding: ItemTagBinding,
        private val tagClick: (String) -> Unit,
        private val notifyAction: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tvTag.setOnClickListener {
                tagClick(binding.tvTag.text.toString())
                notifyAction(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTagViewHolder {
        val itemBinding = ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemTagViewHolder(itemBinding, tagClick, ::selectItem)
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: ItemTagViewHolder, position: Int) {
        val tag = tags[position]
        holder.binding.tvTag.text = tag.tagName

        if (selectedPosition == holder.adapterPosition) {
            holder.binding.tvTag.background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.shape_rounded_blue)
        } else {
            holder.binding.tvTag.background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.shape_rounded_gray)
        }
    }

    private fun selectItem(pos: Int) {
        lastSelectedPosition = selectedPosition
        selectedPosition = pos
        notifyItemChanged(selectedPosition)
        notifyItemChanged(lastSelectedPosition)
    }
}