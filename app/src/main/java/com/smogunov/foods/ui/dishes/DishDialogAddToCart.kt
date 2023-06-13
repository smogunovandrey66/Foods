package com.smogunov.foods.ui.dishes

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.smogunov.domain.global.models.presentation.Dish
import com.smogunov.foods.databinding.DialogDishItemBinding
import com.smogunov.foods.model.CartViewModel
import com.smogunov.domain.global.utils.log
import org.koin.android.ext.android.inject

class DishDialogAddToCart(private val dish: Dish): DialogFragment() {
    private lateinit var binding: DialogDishItemBinding

    private val cartViewModel: CartViewModel by inject()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        log("DishDialogAddToCart onCreateDialog dish = $dish")
        binding = DialogDishItemBinding.inflate(LayoutInflater.from(requireContext()))
        Glide.with(requireContext())
            .load(dish.imageUrl)
            .into(binding.imgDish)
        binding.tvDescription.text = dish.description
        binding.tvNameDish.text = dish.name
        binding.tvPrice.text = "${dish.price} Р"
        binding.tvWeight.text = "${dish.weight}г"

        binding.btnAddToCart.setOnClickListener{
            cartViewModel.changeCartItem(dish.id, 1)
        }


        return AlertDialog.Builder(context)
//            .setView(R.layout.dialog_dish_item) - Вараиант через xml
            .setView(binding.root)
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        log("DishDialogAddToCart onViewCreated dish = $dish")

        super.onViewCreated(view, savedInstanceState)
    }
}