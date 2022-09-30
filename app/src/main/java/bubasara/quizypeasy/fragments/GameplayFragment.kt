package bubasara.quizypeasy.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import bubasara.quizypeasy.R
import bubasara.quizypeasy.databinding.FragmentGameplayBinding
import bubasara.quizypeasy.models.Question
import bubasara.quizypeasy.models.QuizyPeasyApplication
import bubasara.quizypeasy.utils.getListOfCategoriesFromJson
import bubasara.quizypeasy.viewmodels.QuestionViewModel
import bubasara.quizypeasy.viewmodels.QuestionViewModelFactory
import bubasara.quizypeasy.viewmodels.SharedViewModel

class GameplayFragment : Fragment(R.layout.fragment_gameplay) {

    private var _binding: FragmentGameplayBinding? = null
    private val binding get() = _binding!!

    private var gameplayRandomQuestions: ArrayList<Question> = arrayListOf()
    private var index = 0


    private val questionViewModel: QuestionViewModel by viewModels {
        QuestionViewModelFactory(
            (activity?.application as QuizyPeasyApplication).database.questionDao()
        )
    }

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Toast.makeText(requireContext(), "Game in progress...", Toast.LENGTH_SHORT).show()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initQuestionsData()

        Handler(Looper.getMainLooper()).postDelayed({
            generateQuestions()
        }, 2000)

        binding.layoutGameplay.btnAnswerA.setOnClickListener {
            markAnswer(binding.layoutGameplay.btnAnswerA)
        }
        binding.layoutGameplay.btnAnswerB.setOnClickListener {
            markAnswer(binding.layoutGameplay.btnAnswerB)
        }
        binding.layoutGameplay.btnAnswerC.setOnClickListener {
            markAnswer(binding.layoutGameplay.btnAnswerC)
        }
        binding.layoutGameplay.btnAnswerD.setOnClickListener {
            markAnswer(binding.layoutGameplay.btnAnswerD)
        }

        //click on Previous button -> get previous question
        binding.btnPrevious.setOnClickListener {
            showPreviousQuestion()
        }

        //click on Next button -> get next question
        binding.btnSkip.setOnClickListener {
            showNextQuestion()
        }

        //click on Quit button -> go to Leave dialog fragment
        binding.btnQuit.setOnClickListener {
            findNavController().navigate(R.id.action_gamePlayFragment_to_leaveDialog)
        }
    }

    //  when answer is clicked, animate it and go to next question
    private fun markAnswer(button: Button) {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.9f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.9f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(
            button, scaleX, scaleY)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(binding.layoutGameplay.btnAnswerA)
        animator.disableViewDuringAnimation(binding.layoutGameplay.btnAnswerB)
        animator.disableViewDuringAnimation(binding.layoutGameplay.btnAnswerC)
        animator.disableViewDuringAnimation(binding.layoutGameplay.btnAnswerD)
        animator.disableViewDuringAnimation(binding.btnPrevious)
        animator.disableViewDuringAnimation(binding.btnSkip)
        animator.disableViewDuringAnimation(binding.btnQuit)

        animator.start()
        animator.doOnEnd {
            showNextQuestion()
        }
    }

    /*  extension method listens for start/end events on an animation and disables
        the given view for until animation is ended */
    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {

        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

    /*  retrieve questions from room db if it contains data
        else read json, write to db and then retrieve data from db    */
    private fun initQuestionsData() {
        questionViewModel.retrieveAllQuestions().observe(this.viewLifecycleOwner) { allQuestions ->

            //  if question table in db is empty
            if (allQuestions.isNullOrEmpty()) {
                for (categoryWithQuestions in getListOfCategoriesFromJson(requireContext())) {

                    // insert questions into db
                    for (question in categoryWithQuestions.listOfQuestions) {
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

    //  generate 10 random questions out of questions from checked categories
    private fun generateQuestions() {

        //  from checked categories from ChooseCategoriesFragment
        val categories = sharedViewModel.listOfCheckedCategories   //categories ids

        //  get questions
        questionViewModel.retrieveQuestionsFromCategories(categories)
            .observe(this.viewLifecycleOwner) { questions ->
                //  and pick 10 random questions for the gameplay
                while (gameplayRandomQuestions.size < 10) {
                    val randomQuestion = questions.random()
                    if (gameplayRandomQuestions.contains(randomQuestion)) {
                        continue
                    } else {
                        gameplayRandomQuestions.add(randomQuestion)
                    }
                }
                initFirstQuestion()
            }
    }

    //  when first question is loaded, remove loading bar and show question
    private fun loadFirstQuestion() {
        binding.layoutGameplay.loadingBar.visibility = View.GONE
        binding.layoutGameplay.txtViewQuestionContent.visibility = View.VISIBLE
    }

    // starting game by showing first question
    private fun initFirstQuestion() {
        gameplayRandomQuestions.shuffle()
        loadFirstQuestion()
        binding.layoutGameplay.txtViewQuestionContent.text = gameplayRandomQuestions[index].question
        binding.layoutGameplay.btnAnswerA.text = gameplayRandomQuestions[index].answerA
        binding.layoutGameplay.btnAnswerB.text = gameplayRandomQuestions[index].answerB
        binding.layoutGameplay.btnAnswerC.text = gameplayRandomQuestions[index].answerC
        binding.layoutGameplay.btnAnswerD.text = gameplayRandomQuestions[index].answerD
    }

    private fun showPreviousQuestion() {
        if (index == 1) {
            index -= 1
            binding.layoutGameplay.txtViewQuestionContent.text = gameplayRandomQuestions[index].question
            binding.layoutGameplay.btnAnswerA.text = gameplayRandomQuestions[index].answerA
            binding.layoutGameplay.btnAnswerB.text = gameplayRandomQuestions[index].answerB
            binding.layoutGameplay.btnAnswerC.text = gameplayRandomQuestions[index].answerC
            binding.layoutGameplay.btnAnswerD.text = gameplayRandomQuestions[index].answerD
        } else if (index > 1) {
            index -= 1
            binding.layoutGameplay.txtViewQuestionContent.text = gameplayRandomQuestions[index].question
            binding.layoutGameplay.btnAnswerA.text = gameplayRandomQuestions[index].answerA
            binding.layoutGameplay.btnAnswerB.text = gameplayRandomQuestions[index].answerB
            binding.layoutGameplay.btnAnswerC.text = gameplayRandomQuestions[index].answerC
            binding.layoutGameplay.btnAnswerD.text = gameplayRandomQuestions[index].answerD
        }
    }

    private fun showNextQuestion() {
        if (index == 0) {
            index += 1
            binding.layoutGameplay.txtViewQuestionContent.text = gameplayRandomQuestions[index].question
            binding.layoutGameplay.btnAnswerA.text = gameplayRandomQuestions[index].answerA
            binding.layoutGameplay.btnAnswerB.text = gameplayRandomQuestions[index].answerB
            binding.layoutGameplay.btnAnswerC.text = gameplayRandomQuestions[index].answerC
            binding.layoutGameplay.btnAnswerD.text = gameplayRandomQuestions[index].answerD
        } else if (index < gameplayRandomQuestions.size - 1) {
            index += 1
            binding.layoutGameplay.txtViewQuestionContent.text = gameplayRandomQuestions[index].question
            binding.layoutGameplay.btnAnswerA.text = gameplayRandomQuestions[index].answerA
            binding.layoutGameplay.btnAnswerB.text = gameplayRandomQuestions[index].answerB
            binding.layoutGameplay.btnAnswerC.text = gameplayRandomQuestions[index].answerC
            binding.layoutGameplay.btnAnswerD.text = gameplayRandomQuestions[index].answerD
        } else {
            findNavController().navigate(R.id.action_gamePlayFragment_to_statsFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}