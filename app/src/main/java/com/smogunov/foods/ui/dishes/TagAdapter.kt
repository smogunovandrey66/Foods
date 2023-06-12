package com.smogunov.foods.ui.dishes

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.smogunov.domain.global.models.presentation.Tag
import com.smogunov.foods.R

class TagAdapter(private val tags: List<Tag>, private val tagClick: (String) -> Unit):
    RecyclerView.Adapter<TagAdapter.ItemTagViewHolder>() {
    class ItemTagViewHolder(view: View, private val tagClick: (String) -> Unit): RecyclerView.ViewHolder(view) {
        val btn: Button
        init {
            btn = view.findViewById(R.id.btn_tag)
            btn.setOnClickListener{
                tagClick(btn.text.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTagViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false)
        return ItemTagViewHolder(view, tagClick)
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: ItemTagViewHolder, position: Int) {
        val tag = tags[position]
        holder.btn.text = tag.tagName
        Log.d("GLOBAL_TAG_LOG", "TagAdapter onBindViewHolder tagName=${tag.tagName}")
    }


}