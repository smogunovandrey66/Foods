package com.smogunov.foods.ui.dishes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.smogunov.domain.global.models.presentation.Dish
import com.smogunov.domain.global.models.presentation.Tag
import com.smogunov.domain.global.resultdata.ResultData
import com.smogunov.domain.global.utils.log
import com.smogunov.foods.databinding.FragmentDishesBinding
import com.smogunov.foods.model.DishesViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * Фрагмент со списком блюд
 */
class DishesFragment : Fragment() {

    private var _binding: FragmentDishesBinding? = null
    private val binding get() = _binding!!

    private val dishesViewModel: DishesViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val nameCategory = arguments?.getString("nameCategory") ?: "empty"
        log("DishesFragment onCreateView nameCategory=$nameCategory")

        _binding = FragmentDishesBinding.inflate(inflater, container, false)
        binding.tvNameCategory.text = nameCategory
        binding.imgBack.setOnClickListener{
            findNavController().popBackStack()
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                dishesViewModel.tagWithDishes.collect{
                    when(it) {
                        ResultData.Loading -> {
                            log("DishesFragment onViewCreated collect dishes = loading")
                        }
                        is ResultData.Error -> {
                            log("DishesFragment onViewCreated collect dishes = error(${it.message})")
                        }
                        is ResultData.Success<*> -> {
                            val dataSuccess = it.value as List<Dish>
                            log("DishesFragment onViewCreated collect dishes = succes($dataSuccess)")
                            val layoutManager = GridLayoutManager(this@DishesFragment.context, 3)
                            binding.rvDishes.layoutManager = layoutManager
                            binding.rvDishes.adapter = DishesAdapter(dataSuccess){dish ->
                                log("DishesFragment onViewCreated onClick dishitem=$dish")
                                showDialogDish(dish)
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                dishesViewModel.tags.collect{
                    when(it) {
                        ResultData.Loading -> {
                            log("DishesFragment onViewCreated collect tags = loading")
                        }
                        is ResultData.Error -> {
                            log("DishesFragment onViewCreated collect tags = error(${it.message})")
                        }
                        is ResultData.Success<*> -> {
                            val dataSuccess = it.value as List<Tag>
                            log("DishesFragment onViewCreated collect tags = succes($dataSuccess)")
                            val linearLayoutManager =
                                LinearLayoutManager(this@DishesFragment.context, LinearLayoutManager.HORIZONTAL, false)
                            binding.rvTags.layoutManager = linearLayoutManager
                            binding.rvTags.adapter = TagAdapter(dataSuccess){tagName->
                                dishesViewModel.load(true, tagName)
                            }
                        }
                    }
                }
            }
        }

        dishesViewModel.load(false, "Все меню")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialogDish(dish: Dish) {
        DishDialogAddToCart(dish).show(parentFragmentManager, "dialog")
    }
}