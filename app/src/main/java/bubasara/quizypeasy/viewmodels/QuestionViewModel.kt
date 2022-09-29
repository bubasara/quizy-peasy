package bubasara.quizypeasy.viewmodels

import androidx.lifecycle.*
import bubasara.quizypeasy.models.Question
import bubasara.quizypeasy.models.QuestionDao
import kotlinx.coroutines.launch

class QuestionViewModel(private val questionDao: QuestionDao) : ViewModel() {

    val allQuestions : LiveData<List<Question>> = questionDao.getQuestions().asLiveData()


    private fun insertQuestion(question: Question) {
        viewModelScope.launch {
            questionDao.insert(question)
        }
    }

    private fun updateQuestion(question: Question) {
        viewModelScope.launch {
            questionDao.update(question)
        }
    }

    private fun deleteQuestion(question : Question){
        viewModelScope.launch {
            questionDao.delete(question)
        }
    }

    //  returns question with given id from db
    fun retrieveQuestion(id : Int) : LiveData<Question> {
        return questionDao.getQuestion(id).asLiveData()
    }

    //  returns list of questions for given category from db
    fun retrieveQuestionsFromCategory(categoryId : Int) : LiveData<List<Question>> {
        return questionDao.getQuestionsFromCategory(categoryId).asLiveData()
    }

    //  returns list of all questions from db
    fun retrieveAllQuestions() : LiveData<List<Question>>{
        return questionDao.getQuestions().asLiveData()
    }

    fun retrieveQuestionsFromCategories(categories : ArrayList<Int>) : LiveData<List<Question>> {
        return questionDao.getQuestionsFromCategories(categories).asLiveData()
    }

    private fun getNewQuestionEntry(question: String, answerA: String, answerB: String, answerC: String,
                                answerD: String, correctAnswer : String, category : Int): Question {
        return Question(
            question = question,
            answerA = answerA,
            answerB = answerB,
            answerC = answerC,
            answerD = answerD,
            correctAnswer = correctAnswer,
            category = category
        )
    }

    fun addNewQuestion(question: String, answerA: String, answerB: String, answerC: String,
                   answerD: String, correctAnswer : String, category : Int) {
        val newQuestion = getNewQuestionEntry(question, answerA, answerB, answerC, answerD,
            correctAnswer, category)
        insertQuestion(newQuestion)
    }

    fun isEntryValid(question: String, answerA: String, answerB: String, answerC: String,
                     answerD: String, correctAnswer : String ): Boolean {
        if (question.isBlank() || answerA.isBlank() || answerB.isBlank() || answerC.isBlank()
            || answerD.isBlank() || correctAnswer.isBlank()) {
            return false
        }
        return true
    }

    fun updateQuestion(
        question: String,
        answerA: String,
        answerB: String,
        answerC: String,
        answerD: String,
        correctAnswer : String,
        category: Int
    ){
        val updatedQuestion = getUpdatedQuestionEntry(question, answerA, answerB, answerC,
            answerD, correctAnswer, category)
        updateQuestion(updatedQuestion)
    }

    private fun getUpdatedQuestionEntry(
        question: String,
        answerA: String,
        answerB: String,
        answerC: String,
        answerD: String,
        correctAnswer : String,
        category: Int
    ) : Question {
        return Question(
            question = question,
            answerA = answerA,
            answerB = answerB,
            answerC = answerC,
            answerD = answerD,
            correctAnswer = correctAnswer,
            category = category
        )
    }

}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class QuestionViewModelFactory(private val questionDao: QuestionDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionViewModel(questionDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}