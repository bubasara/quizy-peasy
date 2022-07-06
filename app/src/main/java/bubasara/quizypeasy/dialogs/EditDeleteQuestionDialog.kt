package bubasara.quizypeasy.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import bubasara.quizypeasy.R
import bubasara.quizypeasy.databinding.DialogEditDeleteQuestionBinding
import bubasara.quizypeasy.models.Question
import bubasara.quizypeasy.viewmodels.SharedViewModel

class EditDeleteQuestionDialog : DialogFragment(R.layout.dialog_edit_delete_question) {

    private var _binding : DialogEditDeleteQuestionBinding? = null
    private val binding get() = _binding!!

    val sharedViewModel : SharedViewModel by activityViewModels()

    lateinit var clEditDeleteQuestionContainer : ConstraintLayout
    lateinit var txtViewEditQuestion : TextView
    lateinit var editTxtQuestionContent : EditText
    lateinit var txtViewA : TextView
    lateinit var txtViewB : TextView
    lateinit var txtViewC : TextView
    lateinit var txtViewD : TextView
    lateinit var editTxtA : EditText
    lateinit var editTxtB : EditText
    lateinit var editTxtC : EditText
    lateinit var editTxtD : EditText
    lateinit var txtViewCorrectAnswer : TextView
    lateinit var editTxtCorrectAnswer : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.full_screen_dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogEditDeleteQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clEditDeleteQuestionContainer = binding.clEditDeleteQuestionContainer
        txtViewEditQuestion = binding.txtViewEditQuestion
        editTxtQuestionContent = binding.editTxtQuestionContent
        txtViewA = binding.txtViewA
        txtViewB = binding.txtViewB
        txtViewC = binding.txtViewC
        txtViewD = binding.txtViewD
        editTxtA = binding.editTxtA
        editTxtB = binding.editTxtB
        editTxtC = binding.editTxtC
        editTxtD = binding.editTxtD
        txtViewCorrectAnswer = binding.txtViewCorrectAnswer
        editTxtCorrectAnswer = binding.editTxtCorrectAnswer

        //  get the data about the question from shared view model and show it
        editTxtQuestionContent.setText(sharedViewModel.getNewQuestion()!!.question)
        var tempListOfAnswers = sharedViewModel.getNewQuestion()!!.listOfAnswers
        editTxtA.setText(tempListOfAnswers[0])
        editTxtB.setText(tempListOfAnswers[1])
        editTxtC.setText(tempListOfAnswers[2])
        editTxtD.setText(tempListOfAnswers[3])
        editTxtCorrectAnswer.setText(sharedViewModel.getNewQuestion()!!.correctAnswer)

        //  click on button SAVE -> get data from fields and update the question
        binding.btnSave.setOnClickListener {
            sharedViewModel.setNewQuestion(Question(editTxtQuestionContent.text.toString(),
                arrayListOf(editTxtA.text.toString(), editTxtB.text.toString(),
                    editTxtC.text.toString(), editTxtD.text.toString()), editTxtCorrectAnswer.text.toString()))
            //  set flag for edit to true
            sharedViewModel.questionEditLiveDataBoolean.value = true

            findNavController().popBackStack()
        }

        //  click on button DELETE -> set flag for delete to true and go back
        binding.btnDelete.setOnClickListener {
            sharedViewModel.questionDeleteLiveDataBoolean.value = true
            findNavController().popBackStack()
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