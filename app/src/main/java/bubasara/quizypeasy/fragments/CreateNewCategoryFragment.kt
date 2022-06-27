package bubasara.quizypeasy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import bubasara.quizypeasy.R
import bubasara.quizypeasy.databinding.FragmentCreateNewCategoryBinding

class CreateNewCategoryFragment : Fragment(R.layout.fragment_create_new_category) {

    private var _binding : FragmentCreateNewCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNewCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //click on Add question button -> go to Create New Question fragment
        binding.btnAddQuestion.setOnClickListener {
            findNavController().navigate(R.id.action_createNewCategoryFragment_to_createNewQuestionFragment)
        }

        //click on Create category button -> go back to Choose Categories fragment
        binding.btnCreateCategory.setOnClickListener {
            findNavController().navigate(R.id.action_createNewCategoryFragment_to_chooseCategoriesFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}