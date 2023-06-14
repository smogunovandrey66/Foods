package com.smogunov.foods.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.smogunov.domain.global.models.presentation.Category
import com.smogunov.domain.global.resultdata.ResultData
import com.smogunov.domain.global.utils.log
import com.smogunov.foods.databinding.FragmentCategoryBinding
import com.smogunov.foods.model.CategoriesViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


/**
 * Фрагмент для категорий
 */
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val categoryViewModel: CategoriesViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        log("CategoryFragment onCreateView categoryViewModel=$categoryViewModel")
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoryViewModel.category.collect {
                    when (it) {
                        ResultData.Loading -> {
                            log("CategoryFragment onViewCreated: resultData=Loading")
                        }

                        is ResultData.Error -> {
                            log("CategoryFragment onViewCreated: resultData=Error(${it.message})")
                        }

                        else -> {
                            val dataSuccess = it as ResultData.Success<*>
                            val categories = dataSuccess.value as List<Category>
                            log("CategoryFragment onViewCreated: resultData=Success($categories)")
                            val linearLayoutManager =
                                LinearLayoutManager(
                                    this@CategoryFragment.context,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                            binding.rvCategory.layoutManager = linearLayoutManager
                            binding.rvCategory.adapter = CategoryAdapter(categories)
                        }
                    }
                }
            }
        }

        categoryViewModel.load(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}