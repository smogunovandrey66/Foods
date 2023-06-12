package com.smogunov.foods.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smogunov.domain.global.models.presentation.Category
import com.smogunov.foods.R

class CategoryAdapter(private val categories: List<Category>):
    RecyclerView.Adapter<CategoryAdapter.ItemCategoryViewHolder>() {
    class ItemCategoryViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image: ImageView
        val text: TextView
        var category: Category? = null
        init {
            image = view.findViewById(R.id.img_category)
            text = view.findViewById(R.id.tv_name_category)
            view.setOnClickListener{
                val args = if(category != null)
                    bundleOf("nameCategory" to category!!.name)
                else
                    null
                view.findNavController().navigate(R.id.item_dishes, args)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ItemCategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: ItemCategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.text.text = category.name
        holder.category = category
        Glide
            .with(holder.itemView.context)
            .load(category.imageUrl)
            .into(holder.image)
    }

}