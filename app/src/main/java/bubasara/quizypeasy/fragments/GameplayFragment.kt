package bubasara.quizypeasy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import bubasara.quizypeasy.R
import bubasara.quizypeasy.databinding.FragmentGameplayBinding
import bubasara.quizypeasy.models.QuizyPeasyApplication
import bubasara.quizypeasy.utils.getListOfCategoriesFromJson
import bubasara.quizypeasy.viewmodels.QuestionViewModel
import bubasara.quizypeasy.viewmodels.QuestionViewModelFactory

class GameplayFragment : Fragment(R.layout.fragment_gameplay) {

    private var _binding : FragmentGameplayBinding? = null
    private val binding get() = _binding!!

    private val questionViewModel : QuestionViewModel by viewModels {
        QuestionViewModelFactory(
            (activity?.application as QuizyPeasyApplication).database.questionDao()
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initQuestionsData()

        //click on Previous button -> get previous question
        binding.btnPrevious.setOnClickListener {
            //todo
        }

        //click on Next button -> get next question
        binding.btnNext.setOnClickListener {
            //todo
            //for testing purposes
            findNavController().navigate(R.id.action_gamePlayFragment_to_statsFragment)
        }

        //click on Quit button -> go to Leave dialog fragment
        binding.btnQuit.setOnClickListener {
            findNavController().navigate(R.id.action_gamePlayFragment_to_leaveDialog)
        }
    }

    private fun initQuestionsData() {

        //questionViewModel.retrieveQuestionsFromCategory(1).observe(this.viewLifecycleOwner) { allQuestions ->
        questionViewModel.retrieveAllQuestions().observe(this.viewLifecycleOwner) { allQuestions ->

            if(allQuestions.isNullOrEmpty())
            {
                for (categoryWithQuestions in getListOfCategoriesFromJson(requireContext())) {
                    //insertuj u bazu


                    for(question in categoryWithQuestions.listOfQuestions) {

                        questionViewModel.addNewQuestion(
                            question.question,
                            question.answerA,
                            question.answerB,
                            question.answerC,
                            question.answerD,
                            question.correctAnswer,
                            question.category
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}