package bubasara.quizypeasy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import bubasara.quizypeasy.R
import bubasara.quizypeasy.models.Question

class CreateNewCategoryAdapter(var context : Context, private val interfaceListener : CreateNewCategoryInterface)
    : RecyclerView.Adapter<CreateNewCategoryAdapter.ViewHolder>() {

    //  creating list of questions
    var listOfQuestions = ArrayList<Question>()

    internal fun setListOfQuestions(listOfQuestions : ArrayList<Question>){
        this.listOfQuestions = listOfQuestions
    }

    //  interface for long click on question -> open edit/delete dialog
    interface CreateNewCategoryInterface {
        fun longClickOnQuestion(position: Int)
    }

    //  elements in recyclerview (item gameplay)
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val clQuestionContainer : ConstraintLayout
        private val txtViewQuestionContent : TextView
        private val txtViewAnswers : TextView
        private val btnAnswerA : Button
        private val btnAnswerB : Button
        private val btnAnswerC : Button
        private val btnAnswerD : Button
        private var txtViewCorrectAnswer : TextView
        private var editTxtCorrectAnswer : EditText

        init {
            clQuestionContainer = view.findViewById(R.id.constraintLayoutQuestionContainer)
            txtViewQuestionContent = view.findViewById(R.id.txtViewQuestionContent)
            txtViewAnswers = view.findViewById(R.id.txtViewAnswers)
            btnAnswerA = view.findViewById(R.id.btnAnswerA)
            btnAnswerB = view.findViewById(R.id.btnAnswerB)
            btnAnswerC = view.findViewById(R.id.btnAnswerC)
            btnAnswerD = view.findViewById(R.id.btnAnswerD)
            txtViewCorrectAnswer = view.findViewById(R.id.txtViewCorrectAnswer)
            editTxtCorrectAnswer = view.findViewById(R.id.editTxtCorrectAnswer)
        }

        /*  getters */
        fun getClQuestionContainer() : ConstraintLayout {
            return clQuestionContainer
        }

        fun getTxtViewQuestionContent() : TextView {
            return txtViewQuestionContent
        }

        fun getBtnAnswerA() : Button {
            return btnAnswerA
        }

        fun getBtnAnswerB() : Button {
            return btnAnswerB
        }

        fun getBtnAnswerC() : Button {
            return btnAnswerC
        }

        fun getBtnAnswerD() : Button {
            return btnAnswerD
        }

        fun getTxtViewCorrectAnswer() : TextView {
            return txtViewCorrectAnswer
        }

        fun getEditTxtCorrectAnswer() : EditText {
            return editTxtCorrectAnswer
        }
        /*  end of getters */

    }

    //  inflate item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gameplay, parent,false)
        return ViewHolder(view)
    }

    //  binding elements with data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = listOfQuestions[position]

        //  question content
        holder.getTxtViewQuestionContent().text = question.question

        //  on long click -> edit/delete question
        holder.getClQuestionContainer().setOnLongClickListener {
            interfaceListener.longClickOnQuestion(position)
            true
        }
    }

    //  num of items for recyclerview
    override fun getItemCount(): Int {
        return listOfQuestions.size
    }

    //  set new (edit) text for question on clicked position
    fun editItem(position: Int, questionContent: String){
        listOfQuestions[position].question = questionContent
        notifyItemChanged(position)
    }

    //  delete question on clicked position
    fun deleteItem(position: Int){
        listOfQuestions.removeAt(position)
        notifyItemRemoved(position)
    }
}