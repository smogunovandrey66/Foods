package com.smogunov.foods.ui.dishes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smogunov.domain.global.models.presentation.Dish
import com.smogunov.foods.R

class DishesAdapter(private val dishes: List<Dish>, private val onClickDish: (Dish) -> Unit):
    RecyclerView.Adapter<DishesAdapter.ItemDishViewHolder>() {
    class ItemDishViewHolder(view: View, private val  onClickDish: (Dish) -> Unit): RecyclerView.ViewHolder(view) {
        var dish: Dish? = null
        val image: ImageView
        val caption: TextView
        init {
            image = view.findViewById(R.id.img_dish)
            caption = view.findViewById(R.id.tv_name_dish)
            view.setOnClickListener{
                dish?.let { dish ->
                    onClickDish(dish)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDishViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dish, parent, false)
        return ItemDishViewHolder(view, onClickDish)
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    override fun onBindViewHolder(holder: ItemDishViewHolder, position: Int) {
        val dish = dishes[position]
        holder.dish = dish
        holder.caption.text = dish.name
        Glide.with(holder.itemView.context)
            .load(dish.imageUrl)
            .into(holder.image)
    }
}