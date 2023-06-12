package com.smogunov.foods.ui.dishes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.smogunov.domain.global.models.presentation.Dish
import com.smogunov.domain.global.models.presentation.Tag
import com.smogunov.domain.global.models.presentation.TagWithDishes
import com.smogunov.domain.global.resultdata.ResultData
import com.smogunov.foods.R
import com.smogunov.foods.databinding.DialogDishItemBinding
import com.smogunov.foods.databinding.FragmentDishesBinding
import com.smogunov.foods.model.DishesViewModel
import com.smogunov.foods.utils.log
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DishesFragment : Fragment() {

    private var _binding: FragmentDishesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val dishesViewModel: DishesViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                dishesViewModel.tagWithDishes.collect{ it ->
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
                            val layoutManager: GridLayoutManager = GridLayoutManager(this@DishesFragment.context, 3)
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
                            binding.rvTags.adapter = TagAdapter(dataSuccess){
                                dishesViewModel.load(true, it)
                            }
                        }
                    }
                }
            }
        }

        dishesViewModel.load(false, "Все меню")



//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialogDish(dish: Dish) {
        DishDialogAddToCart(dish).show(parentFragmentManager, "dialog")
    }
}