package bubasara.quizypeasy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import bubasara.quizypeasy.R
import bubasara.quizypeasy.models.Question

class CreateNewCategoryAdapter(var context : Context)
    : RecyclerView.Adapter<CreateNewCategoryAdapter.ViewHolder>() {

    //  creating list of questions
    var listOfQuestions = ArrayList<Question>()

    internal fun setListOfQuestions(listOfQuestions : ArrayList<Question>){
        this.listOfQuestions = listOfQuestions
    }

    //  elements in recyclerview (item gameplay)
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val clQuestionContainer : ConstraintLayout
        private val txtViewQuestionContent : TextView
        private val txtViewAnswers : TextView
        private val txtViewAnswerA : TextView
        private val txtViewAnswerB : TextView
        private val txtViewAnswerC : TextView
        private val txtViewAnswerD : TextView

        init {
            clQuestionContainer = view.findViewById(R.id.constraintLayoutQuestionContainer)
            txtViewQuestionContent = view.findViewById(R.id.txtViewQuestionContent)
            txtViewAnswers = view.findViewById(R.id.txtViewAnswers)
            txtViewAnswerA = view.findViewById(R.id.txtViewA)
            txtViewAnswerB = view.findViewById(R.id.txtViewB)
            txtViewAnswerC = view.findViewById(R.id.txtViewC)
            txtViewAnswerD = view.findViewById(R.id.txtViewD)
        }

        //  getters
        fun getClQuestionContainer() : ConstraintLayout {
            return clQuestionContainer
        }

        fun getTxtViewQuestionContent() : TextView {
            return txtViewQuestionContent
        }

        fun getTxtViewAnswerA() : TextView {
            return txtViewAnswerA
        }

        fun getTxtViewAnswerB() : TextView {
            return txtViewAnswerB
        }

        fun getTxtViewAnswerC() : TextView {
            return txtViewAnswerC
        }

        fun getTxtViewAnswerD() : TextView {
            return txtViewAnswerD
        }
    }

    //  inflate item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gameplay, parent,false)
        return CreateNewCategoryAdapter.ViewHolder(view)
    }

    //  binding elements with data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = listOfQuestions[position]

        //  question content
        holder.getTxtViewQuestionContent().text = question.question

        //  on long click -> edit/delete question
        holder.getClQuestionContainer().setOnLongClickListener {
            //  todo
            true
        }
    }

    //  num of items for recyclerview
    override fun getItemCount(): Int {
        return listOfQuestions.size
    }

    //  todo
    fun editItem(){

    }

    fun deleteItem(){

    }
}