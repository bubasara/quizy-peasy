package bubasara.quizypeasy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import bubasara.quizypeasy.R
import bubasara.quizypeasy.adapters.ChooseCategoriesAdapter
import bubasara.quizypeasy.databinding.FragmentChooseCategoriesBinding
import bubasara.quizypeasy.models.QuizyPeasyApplication
import bubasara.quizypeasy.utils.getListOfCategoriesFromJson
import bubasara.quizypeasy.viewmodels.CategoryViewModel
import bubasara.quizypeasy.viewmodels.CategoryViewModelFactory
import bubasara.quizypeasy.viewmodels.SharedViewModel

class ChooseCategoriesFragment : Fragment(R.layout.fragment_choose_categories) {

    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory(
            (activity?.application as QuizyPeasyApplication).database.categoryDao()
        )
    }

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var chooseCategoriesAdapter: ChooseCategoriesAdapter
    private var hasCategoryCheckStateChanged = false
    var deletePosition = -1

    private var _binding: FragmentChooseCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCategoriesData()

        chooseCategoriesAdapter = ChooseCategoriesAdapter(requireContext(),

            object : ChooseCategoriesAdapter.ChooseCategoriesAdapterInterface {
                //  long click on category -> delete dialog
                override fun longClickOnCategory(position: Int) {
                    deletePosition = position
                    findNavController().navigate(R.id.action_chooseCategoriesFragment_to_deleteCategoryDialog)
                }

                //  click on category check square -> checked
                override fun clickOnIsChecked() {
                    hasCategoryCheckStateChanged = true
                }
            }
        )

        //  data from view model is transferred to adapter
        chooseCategoriesAdapter.setListOfCategories(categoryViewModel.allCategoriesMutable.value ?: listOf())
        binding.recyclerViewCategories.adapter = chooseCategoriesAdapter
        binding.recyclerViewCategories.layoutManager = LinearLayoutManager(context)

        // todo -> when new category is being added
/*        if(sharedViewModel.getNewCategory() != null) {
            viewModel.listOfCategories.add(sharedViewModel.getNewCategory()!!)
            chooseCategoriesAdapter.notifyDataSetChanged()

            PreferenceManager.setListOfCategories(viewModel.listOfCategories)
            sharedViewModel.resetNewCategory()
        }*/


        //  observer for deleting category
        sharedViewModel.categoryDeleteLiveDataBoolean.observe(viewLifecycleOwner) {
            if (it) {

                categoryViewModel.deleteCategory(categoryViewModel.allCategoriesMutable.value!![deletePosition])

                //viewModel.allCategoriesMutable.value?.removeAt(deletePosition)
                //chooseCategoriesAdapter.deleteItem(deletePosition)

                //PreferenceManager.setListOfCategories(viewModel.listOfCategories)
                chooseCategoriesAdapter.notifyItemRemoved(deletePosition)
                chooseCategoriesAdapter.notifyItemRangeChanged(
                    deletePosition,
                    categoryViewModel.allCategoriesMutable.value?.size ?: 1
                )

                sharedViewModel.categoryDeleteLiveDataBoolean.value = false
            }
        }

        //click on Create new category button -> go to Create New Category fragment
        binding.btnCreateNewCategory.setOnClickListener {
            findNavController().navigate(R.id.action_chooseCategoriesFragment_to_createNewCategoryFragment)
        }

        //click on Play button -> go to Gameplay fragment
        binding.btnPlay.setOnClickListener {
            //  todo svm list of questions for chosen categories
            findNavController().navigate(R.id.action_chooseCategoriesFragment_to_gamePlayFragment)
        }
    }

    private fun initCategoriesData() {
        //  if there is no records of categories in db
        categoryViewModel.retrieveAllCategories().observe(this.viewLifecycleOwner) { allCategories ->

            if(allCategories.isNullOrEmpty())
            {
                for (categoryWithQuestions in getListOfCategoriesFromJson(requireContext())) {
                    // insert categories from json
                    categoryViewModel.addNewCategory(categoryWithQuestions.categoryName, categoryWithQuestions.numberOfQuestions, categoryWithQuestions.isChecked, categoryWithQuestions.imgCategory)
                }
            }

            categoryViewModel.allCategoriesMutable = MutableLiveData(allCategories.toMutableList())
            chooseCategoriesAdapter.setListOfCategories(allCategories)
            chooseCategoriesAdapter.notifyDataSetChanged()
        }
    }





    override fun onPause() {
        super.onPause()
        //  if category checked/unchecked
        if (hasCategoryCheckStateChanged) {
            //PreferenceManager.setListOfCategories(viewModel.listOfCategories)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}