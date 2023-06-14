package com.smogunov.foods.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smogunov.domain.global.models.presentation.CartItem
import com.smogunov.foods.databinding.ItemCartBinding

/**
 * Адаптер для элементов корзины
 */
class CartAdapter(
    private val cartItems: List<CartItem>,
    private val onClickCart: (Int, Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.ItemCartViewHolder>() {
    class ItemCartViewHolder(
        val binding: ItemCartBinding,
        private val onClickCart: (Int, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        var cartItem: CartItem? = null

        init {
            binding.imgMinus.setOnClickListener {
                cartItem?.let {
                    onClickCart(it.dish.id, -1)
                }
            }
            binding.imgPlus.setOnClickListener {
                cartItem?.let {
                    onClickCart(it.dish.id, +1)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCartViewHolder {
        val itemBinding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemCartViewHolder(itemBinding, onClickCart)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    override fun onBindViewHolder(holder: ItemCartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.cartItem = cartItem
        Glide.with(holder.itemView.context)
            .load(cartItem.dish.imageUrl)
            .into(holder.binding.imgDish)
        holder.binding.tvCount.text = "${cartItem.count}"
        holder.binding.tvNameDish.text = cartItem.dish.name
        holder.binding.tvPrice.text = "${cartItem.dish.price} Р"
        holder.binding.tvWeight.text = "${cartItem.dish.weight}г"
    }

}