package bubasara.quizypeasy.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import bubasara.quizypeasy.R
import bubasara.quizypeasy.databinding.DialogCreateNewQuestionBinding
import bubasara.quizypeasy.models.Question
import bubasara.quizypeasy.viewmodels.SharedViewModel

class CreateNewQuestionFragment : DialogFragment(R.layout.dialog_create_new_question) {

    private var _binding : DialogCreateNewQuestionBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogCreateNewQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //click on Create button -> go to Create New Category fragment
        binding.btnCreate.setOnClickListener {
            binding.btnCreate.setOnClickListener {
                if (binding.editTxtQuestionContent.text.toString()
                        .isEmpty() || binding.editTxtQuestionContent.text.toString().isBlank()
                    || binding.editTxtA.text.toString()
                        .isEmpty() || binding.editTxtA.text.toString().isBlank()
                    || binding.editTxtB.text.toString()
                        .isEmpty() || binding.editTxtB.text.toString().isBlank()
                    || binding.editTxtC.text.toString()
                        .isEmpty() || binding.editTxtC.text.toString().isBlank()
                    || binding.editTxtD.text.toString()
                        .isEmpty() || binding.editTxtD.text.toString().isBlank()
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Neither the question or the answers can be blank. Please fill in the blanks.",
                        Toast.LENGTH_LONG
                    )
                } else {
                    //  getting data for question
                    val editTxtQuestionContent = binding.editTxtQuestionContent.text.toString()
                    val txtViewA = binding.txtViewA.text.toString()
                    val txtViewB = binding.txtViewB.text.toString()
                    val txtViewC = binding.txtViewC.text.toString()
                    val txtViewD = binding.txtViewD.text.toString()

                    //  creating question with collected data
                    sharedViewModel.setNewQuestion(
                        Question(
                            editTxtQuestionContent,
                            arrayListOf<String>(txtViewA, txtViewB, txtViewC, txtViewD), 1
                        )
                    )
                }
                findNavController().navigate(R.id.action_createNewQuestionFragment_to_createNewCategoryFragment)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}