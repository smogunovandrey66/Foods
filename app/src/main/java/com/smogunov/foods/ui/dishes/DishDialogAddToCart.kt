package com.smogunov.foods.ui.dishes

import android.app.AlertDialog
import android.app.Dialog
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.smogunov.domain.global.models.presentation.CartItem
import com.smogunov.domain.global.models.presentation.Dish
import com.smogunov.domain.global.resultdata.ResultData
import com.smogunov.foods.databinding.DialogDishItemBinding
import com.smogunov.foods.model.CartViewModel
import com.smogunov.domain.global.utils.log
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
        binding.imgPlus.setOnClickListener {
            cartViewModel.changeCartItem(dish.id, 1)
        }
        binding.imgMinus.setOnClickListener {
            cartViewModel.changeCartItem(dish.id, -1)
        }




        return AlertDialog.Builder(context)
//            .setView(R.layout.dialog_dish_item) - Вараиант через xml
            .setView(binding.root)
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        log("DishDialogAddToCart onViewCreated dish = $dish")

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartViewModel.resultChangeCartItem.collect{
                    when(it) {
                        ResultData.Loading -> {
                            log("DishDialogAddToCart collect resultChangeCartItem Loading")
                        }
                        is ResultData.Error -> {
                            log("DishDialogAddToCart collect resultChangeCartItem Error(${it.message})")
                        }
                        is ResultData.Success<*> -> {
                            log("DishDialogAddToCart collect resultChangeCartItem Success")
                            cartViewModel.loadCartItems()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartViewModel.cartItems.collect{
                    when(it) {
                        ResultData.Loading -> {
                            log("DishDialogAddToCart collect cartItems Loading")
                        }
                        is ResultData.Error -> {
                            log("DishDialogAddToCart collect cartItems Error(${it.message})")
                        }
                        is ResultData.Success<*> -> {
                            val cartItems = it.value as List<CartItem>
                            val cartItem = cartItems.find {cartItem ->
                                cartItem.dish.id == dish.id
                            }
                            log("DishDialogAddToCart collect cartItems succes, cartItem=$cartItem")
                            if(cartItem == null) {
                                binding.layoutChangeCount.visibility = View.INVISIBLE
                                binding.btnAddToCart.visibility = View.VISIBLE
                            } else {
                                binding.layoutChangeCount.visibility = View.VISIBLE
                                binding.btnAddToCart.visibility = View.INVISIBLE
                            }
                        }
                    }
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}