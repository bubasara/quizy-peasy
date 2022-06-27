package bubasara.quizypeasy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import bubasara.quizypeasy.R
import bubasara.quizypeasy.databinding.FragmentChooseCategoriesBinding

class ChooseCategoriesFragment : Fragment(R.layout.fragment_choose_categories) {

    private var _binding : FragmentChooseCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //todo long click on category -> delete dialog
        //todo click on category -> checked

        //click on Create new category button -> go to Create New Category fragment
        binding.btnCreateNewCategory.setOnClickListener {
            findNavController().navigate(R.id.action_chooseCategoriesFragment_to_createNewCategoryFragment)
        }

        //click on Play button -> go to Gameplay fragment
        binding.btnPlay.setOnClickListener {
            findNavController().navigate(R.id.action_chooseCategoriesFragment_to_gamePlayFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}