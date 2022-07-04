package bubasara.quizypeasy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import bubasara.quizypeasy.R
import bubasara.quizypeasy.adapters.ChooseCategoriesAdapter
import bubasara.quizypeasy.databinding.FragmentChooseCategoriesBinding
import bubasara.quizypeasy.helpers.PreferenceManager
import bubasara.quizypeasy.viewmodels.ChooseCategoriesViewModel
import bubasara.quizypeasy.viewmodels.SharedViewModel

class ChooseCategoriesFragment : Fragment(R.layout.fragment_choose_categories) {

    private val viewModel : ChooseCategoriesViewModel by viewModels()
    private val sharedViewModel : SharedViewModel by activityViewModels()

    private lateinit var chooseCategoriesAdapter : ChooseCategoriesAdapter
    private var hasCategoryCheckStateChanged = false
    var deletePosition = -1

    private var _binding : FragmentChooseCategoriesBinding? = null
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

        viewModel.listOfCategories = sharedViewModel.listOfCategories

        val recyclerViewCategories = binding.recyclerViewCategories


        this.chooseCategoriesAdapter = ChooseCategoriesAdapter(requireContext(),
            object : ChooseCategoriesAdapter.ChooseCategoriesAdapterInterface{

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
        chooseCategoriesAdapter.setListOfCategories(viewModel.listOfCategories)
        recyclerViewCategories.adapter = chooseCategoriesAdapter
        recyclerViewCategories.layoutManager = LinearLayoutManager(context)

        if(sharedViewModel.getNewCategory() != null) {
            viewModel.listOfCategories.add(sharedViewModel.getNewCategory()!!)
            chooseCategoriesAdapter.notifyDataSetChanged()

            PreferenceManager.setListOfCategories(viewModel.listOfCategories)
            sharedViewModel.resetNewCategory()
        }

        //  observer for deleting category
        sharedViewModel.categoryDeleteLiveDataBoolean.observe(viewLifecycleOwner) {
            if (it) {
                chooseCategoriesAdapter.deleteItem(deletePosition)
                PreferenceManager.setListOfCategories(viewModel.listOfCategories)
                chooseCategoriesAdapter.notifyItemRemoved(deletePosition)
                chooseCategoriesAdapter.notifyItemRangeChanged(
                    deletePosition,
                    viewModel.listOfCategories.size
                )
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

    override fun onPause() {
        super.onPause()
        //  if category checked/unchecked
        if (hasCategoryCheckStateChanged) {
            PreferenceManager.setListOfCategories(viewModel.listOfCategories)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}