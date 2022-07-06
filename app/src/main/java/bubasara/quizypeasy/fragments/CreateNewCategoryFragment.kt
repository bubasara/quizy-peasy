package bubasara.quizypeasy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import bubasara.quizypeasy.R
import bubasara.quizypeasy.adapters.CreateNewCategoryAdapter
import bubasara.quizypeasy.databinding.FragmentCreateNewCategoryBinding
import bubasara.quizypeasy.viewmodels.CreateNewCategoryViewModel
import bubasara.quizypeasy.viewmodels.SharedViewModel

class CreateNewCategoryFragment : Fragment(R.layout.fragment_create_new_category) {

    private val viewModel : CreateNewCategoryViewModel by viewModels()
    val sharedViewModel : SharedViewModel by activityViewModels()

    private var _binding : FragmentCreateNewCategoryBinding? = null
    private val binding get() = _binding!!

    //  photo for new category
    var imgNum = 1  //  until real init
    lateinit var imgViewCategoryImg : ImageView

    private lateinit var adapter : CreateNewCategoryAdapter
    var editDeleteAtThisPosition = -1   //  until real init

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNewCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*  adding random image */
        //  todo user can choose img; add more pics
        imgViewCategoryImg = binding.imgViewCategoryImage
        imgNum = (1..6).random()

        val imgCategory = imgViewCategoryImg.context.resources.getIdentifier("category_${imgNum}",
            "drawable", imgViewCategoryImg.context.packageName)

        imgViewCategoryImg.setImageDrawable(ContextCompat.getDrawable(imgViewCategoryImg.context, imgCategory))
        /*  end of adding random image  */

        //  adapter init
        this.adapter = CreateNewCategoryAdapter(requireContext(),
            object :    CreateNewCategoryAdapter.CreateNewCategoryInterface {
                override fun longClickOnQuestion(position: Int) {
                    editDeleteAtThisPosition = position //  the real init is when question clicked
                    sharedViewModel.setNewQuestion(adapter.listOfQuestions[position])
                    findNavController().navigate(R.id.action_createNewCategoryFragment_to_editDeleteQuestionDialog)
                }
            })

        /*  recyclerView for questions  */
        val recyclerViewQuestions = binding.recyclerViewQuestions
        //sharedViewModel.getNewQuestion()?.let { adapter.listOfQuestions.add(it) }
        adapter.setListOfQuestions(viewModel.listOfQuestions)
        if (sharedViewModel.getNewQuestion() != null){
            adapter.listOfQuestions.add(sharedViewModel.getNewQuestion()!!)
            adapter.notifyDataSetChanged()
        }
        recyclerViewQuestions.layoutManager = GridLayoutManager(context, 2) //two columns
        recyclerViewQuestions.adapter = adapter
        /*  end of recyclerView for questions   */

        //click on Add question button -> go to Create New Question fragment
        binding.btnAddQuestion.setOnClickListener {
            findNavController().navigate(R.id.action_createNewCategoryFragment_to_createNewQuestionFragment)
        }

        //click on Create category button -> go back to Choose Categories fragment
        binding.btnCreateCategory.setOnClickListener {

            //  validation
            //  if category has no name
            if(binding.editTxtCategoryName.text.isEmpty() || binding.editTxtCategoryName.text.isBlank()
                || binding.editTxtCategoryName.text == null){

                Toast.makeText(requireContext(), "Category name must not be blank...",
                    Toast.LENGTH_SHORT).show()

            } //  if category has no questions
            else if (adapter.listOfQuestions.size == 0) {
                Toast.makeText(requireContext(), "Category must not be empty. Please add a question.",
                    Toast.LENGTH_SHORT).show()
            }


            else { //  getting data for category
//                adapter.listOfQuestions.add(sharedViewModel.getNewQuestion()!!)
//                adapter.notifyDataSetChanged()
                val imgNum = imgNum
                val categoryName = binding.editTxtCategoryName.text.toString()
                val numberOfQuestions = adapter.listOfQuestions.size
                val listOfQuestions = adapter.listOfQuestions

                //  creating category with collected data
                sharedViewModel.setNewCategory(categoryName, numberOfQuestions,true, listOfQuestions, imgNum)

                findNavController().popBackStack()
            }

        }

        /*  observer for editing question
            when question is edited, observer transfers new data from dialog to recyclerView    */
        sharedViewModel.questionEditLiveDataBoolean.observe(viewLifecycleOwner) {
            if (it) {
                adapter.editItem(
                    editDeleteAtThisPosition,
                    sharedViewModel.getNewQuestion()!!.question,
                    sharedViewModel.getNewQuestion()!!.listOfAnswers[0],
                    sharedViewModel.getNewQuestion()!!.listOfAnswers[1],
                    sharedViewModel.getNewQuestion()!!.listOfAnswers[2],
                    sharedViewModel.getNewQuestion()!!.listOfAnswers[3],
                    sharedViewModel.getNewQuestion()!!.correctAnswer,
                )
                sharedViewModel.questionEditLiveDataBoolean.value = false
                adapter.notifyItemChanged(editDeleteAtThisPosition)
            }
        }

        /*  observer for deleting question
            when question is deleted, observer deletes old data from recyclerView    */
        sharedViewModel.questionDeleteLiveDataBoolean.observe(viewLifecycleOwner) {
            if (it) {
                adapter.deleteItem(editDeleteAtThisPosition)
                sharedViewModel.questionDeleteLiveDataBoolean.value = false
                adapter.notifyItemRemoved(editDeleteAtThisPosition)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}