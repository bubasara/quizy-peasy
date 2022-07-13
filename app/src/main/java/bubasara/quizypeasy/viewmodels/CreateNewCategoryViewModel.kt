package bubasara.quizypeasy.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import bubasara.quizypeasy.models.Category
import bubasara.quizypeasy.models.CategoryDao
import bubasara.quizypeasy.models.Question
import bubasara.quizypeasy.models.QuizyPeasyApplication
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class CreateNewCategoryViewModel(private val categoryDao : CategoryDao) : ViewModel() {

    lateinit var category: Category

    /*  list of questions
        arraylist consists of
        Map < Map <question, list of answers>, correct answer >
     */
    /*var listOfQuestions = arrayListOf<Map<Map<String, ArrayList<String>>, Int>>()*/

    var listOfQuestions = arrayListOf<Question>()

    //for testing purposes, delete later
    /*init {
        makeTestingListOfQuestions()
    }*/

    /*private fun makeTestingListOfQuestions(){
       *//* var tempArrayList = arrayListOf("Answer 1", "Answer 2", "Answer 3", "Answer 4")
        var tempQuestion = "Question no 1"
        var tempHashMap = hashMapOf(Pair(tempQuestion, tempArrayList))
        listOfQuestions.add(hashMapOf(Pair(tempHashMap, 1)))

        tempArrayList = arrayListOf("Answer 1", "Answer 2", "Answer 3", "Answer 4")
        tempQuestion = "Question no 2"
        tempHashMap = hashMapOf(Pair(tempQuestion, tempArrayList))
        listOfQuestions.add(hashMapOf(Pair(tempHashMap, 1)))

        tempArrayList = arrayListOf("Answer 1", "Answer 2", "Answer 3", "Answer 4")
        tempQuestion = "Question no 3"
        tempHashMap = hashMapOf(Pair(tempQuestion, tempArrayList))
        listOfQuestions.add(hashMapOf(Pair(tempHashMap, 1)))*//*

        //TODO category id static int
        listOfQuestions.add(Question("Question no 1", "Answer 1", "Answer 2", "Answer 3", "Answer 4", "A", 1))
        listOfQuestions.add(Question("Question no 2", "Answer 1", "Answer 2", "Answer 3", "Answer 4", "B",1))
        listOfQuestions.add(Question("Question no 3", "Answer 1", "Answer 2", "Answer 3", "Answer 4", "C", 1))
        listOfQuestions.add(Question("Question no 4", "Answer 1", "Answer 2", "Answer 3", "Answer 4", "D", 1))
    }*/

    private fun insertCategory(category: Category){
        viewModelScope.launch {
            categoryDao.insert(category)
        }
    }

    fun isEntryValid(categoryName : String): Boolean {
        if (categoryName.isBlank()) {
            return false
        }
        return true
    }

    fun addNewCategory(categoryName: String, numberOfQuestions : Int,
                       isChecked : Boolean, imgCategory : Int){
        val newCategory = getNewCategoryEntry(categoryName, numberOfQuestions, isChecked, imgCategory)
        insertCategory(newCategory)
    }

    private fun getNewCategoryEntry(categoryName : String, numberOfQuestions : Int,
                                    isChecked : Boolean, imgCategory : Int) : Category {
        return Category(
            categoryName = categoryName,
            numberOfQuestions = numberOfQuestions,
            isChecked = isChecked,
            imgCategory = imgCategory
        )
    }
}

class CreateNewCategoryViewModelFactory(private val categoryDao: CategoryDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(CreateNewCategoryViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CreateNewCategoryViewModel(categoryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}