package com.smogunov.foods.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.smogunov.domain.global.models.presentation.CartItem
import com.smogunov.domain.global.resultdata.ResultData
import com.smogunov.domain.global.utils.log
import com.smogunov.foods.databinding.FragmentCartBinding
import com.smogunov.foods.model.CartViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.text.NumberFormat
import java.util.Currency

/**
 * Фрагмент для отображения элементов корзины
 */
class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding
    private val cartVieModel: CartViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartVieModel.cartItems.collect {
                    when (it) {
                        ResultData.Loading -> {
                            log("CartFragment onViewCreated ResultData.Loading")
                        }

                        is ResultData.Error -> {
                            log("CartFragment onViewCreated ResultData.Error=${it.message}")
                        }

                        is ResultData.Success<*> -> {
                            val cartItems = it.value as List<CartItem>
                            val adapter = CartAdapter(cartItems) { idDish, count ->
                                cartVieModel.changeCartItem(idDish, count)
                            }
                            log("CartFragment onViewCreated ResultData.Success=${cartItems}")
                            val linearLayoutManager =
                                LinearLayoutManager(
                                    this@CartFragment.context,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                            binding.rvCart.layoutManager = linearLayoutManager
                            binding.rvCart.adapter = adapter

                            val commonSum = cartItems.sumOf {
                                it.dish.price * it.count
                            }

                            val currencyFormat = NumberFormat.getCurrencyInstance()
                            currencyFormat.maximumFractionDigits = 0
                            currencyFormat.currency = Currency.getInstance("RUB")
                            val commonSumCurrency = currencyFormat.format(commonSum)


                            binding.btnCreateOrder.text = "Оплатить $commonSumCurrency"
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartVieModel.resultChangeCartItem.collect {
                    when (it) {
                        ResultData.Loading -> {

                        }

                        is ResultData.Error -> {

                        }

                        is ResultData.Success<*> -> {
                            cartVieModel.loadCartItems()
                        }
                    }
                }
            }
        }


        cartVieModel.loadCartItems()
    }
}