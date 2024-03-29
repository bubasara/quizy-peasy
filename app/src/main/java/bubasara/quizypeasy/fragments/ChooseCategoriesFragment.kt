package bubasara.quizypeasy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import bubasara.quizypeasy.R
import bubasara.quizypeasy.adapters.ChooseCategoriesAdapter
import bubasara.quizypeasy.databinding.FragmentChooseCategoriesBinding
import bubasara.quizypeasy.models.Category
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
    var hasCategoryCheckStateChanged = false
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

    override fun onResume() {
        super.onResume()

        //  on every return from StatsFragment, uncheck all categories (view and list of categories
        if(::chooseCategoriesAdapter.isInitialized) {
            if(sharedViewModel.listOfCheckedCategories.isEmpty()){
                chooseCategoriesAdapter.uncheckAllCategories()
                chooseCategoriesAdapter.notifyDataSetChanged()
            }
        }
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
                override fun clickOnIsChecked(category : Category) {
                    if(sharedViewModel.listOfCheckedCategories.contains(category.id)){
                        sharedViewModel.removeFromCheckedCategories(category.id)
                    } else {
                        sharedViewModel.addToCheckedCategories(category.id)
                    }
                    hasCategoryCheckStateChanged = true
                }
            }
        )

        //  data from view model is transferred to adapter
        chooseCategoriesAdapter.setListOfCategories(categoryViewModel.allCategoriesMutable.value ?: listOf())
        binding.recyclerViewCategories.adapter = chooseCategoriesAdapter
        binding.recyclerViewCategories.layoutManager = LinearLayoutManager(context)

        // todo -> when new category is being added
        /*if(sharedViewModel.getNewCategory() != null) {
            sharedViewModel.listOfCategories.add(sharedViewModel.getNewCategory()!!)
            chooseCategoriesAdapter.notifyDataSetChanged()

            PreferenceManager.setListOfCategories(sharedViewModel.listOfCategories)
            sharedViewModel.resetNewCategory()
        }*/


        //  observer for deleting category
        sharedViewModel.categoryDeleteLiveDataBoolean.observe(viewLifecycleOwner) {
            if (it) {

                categoryViewModel.deleteCategory(categoryViewModel.allCategoriesMutable.value!![deletePosition])
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
            if(sharedViewModel.listOfCheckedCategories.isEmpty()) {
                Toast.makeText(requireContext(), "Check categories that you want to play.", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_chooseCategoriesFragment_to_gamePlayFragment)
            }
        }
    }

    private fun initCategoriesData() {
        categoryViewModel.retrieveAllCategories().observe(this.viewLifecycleOwner) { allCategories ->

            //  if category table in db is empty
            if(allCategories.isNullOrEmpty())
            {
                for (categoryWithQuestions in getListOfCategoriesFromJson(requireContext())) {
                    // insert categories from json
                    categoryViewModel.addNewCategory(categoryWithQuestions.categoryName, categoryWithQuestions.numberOfQuestions, categoryWithQuestions.isChecked, categoryWithQuestions.imgCategory)
                }
            }

            loadCategories()

            categoryViewModel.allCategoriesMutable = MutableLiveData(allCategories.toMutableList())
            chooseCategoriesAdapter.setListOfCategories(allCategories)
            chooseCategoriesAdapter.notifyDataSetChanged()
        }
    }

    //  when categories are loaded, remove loading bar and show recycler view with categories
    private fun loadCategories() {
        binding.loadingBar.visibility = View.GONE
        binding.recyclerViewCategories.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}