package com.smogunov.foods.ui.dishes

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.smogunov.domain.global.models.presentation.Dish
import com.smogunov.foods.R
import com.smogunov.foods.utils.log

class DishDialogAddToCart(private val dish: Dish): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        log("DishDialogAddToCart onCreateDialog dish = $dish")
        return AlertDialog.Builder(context)
            .setView(R.layout.dialog_dish_item)
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        log("DishDialogAddToCart onViewCreated dish = $dish")

        super.onViewCreated(view, savedInstanceState)
    }
}