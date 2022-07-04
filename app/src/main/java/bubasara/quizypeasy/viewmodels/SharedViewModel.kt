package bubasara.quizypeasy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import bubasara.quizypeasy.models.Category
import bubasara.quizypeasy.models.Question
import bubasara.quizypeasy.utils.getJsonDataFromAsset
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//  shared view model among all fragments, separated by relations (fragment1 - fragment2)
class SharedViewModel(application : Application) : AndroidViewModel(application) {

    /*  SVM: CreateNewCategory - ChooseCategories    */
    private var newCategory : Category? = null

    //  newCategory setter
    fun setNewCategory(categoryName : String, numberOfQuestions : Int, isChecked : Boolean, listOfQuestions : ArrayList<Question>, imgCategory : Int){
        newCategory = Category(categoryName, numberOfQuestions, isChecked, listOfQuestions, imgCategory)
    }

    //  newCategory getter
    fun getNewCategory() : Category? {
        return newCategory
    }

    fun resetNewCategory(){
        newCategory = null
    }

    /*  end of SVM: CreateNewCategory - ChooseCategories    */
    /*  SVM: Choose Categories - Gameplay  */

    /*  data.json   */
    var listOfCategories = arrayListOf<Category>()
    init {
        getListOfCategoriesFromJson(application)
    }

    private fun getListOfCategoriesFromJson(application: Application){
        val json = getJsonDataFromAsset(application, "data.json")
        val list = object : TypeToken<List<Category>>() {}.type
        listOfCategories = Gson().fromJson(json, list)
    }

    /*  end of SVM: Choose Categories - Gameplay  */

    /*  SVM: Create New Category - Choose Categories    */
    private var newQuestion : Question? = null

    //  getter
    fun getNewQuestion() : Question? {
        return newQuestion
    }

    //  setter
    fun setNewQuestion(question: Question){
        newQuestion = question
    }

    //  reset EditText when question submitted
    fun resetNewQuestion(){
        newQuestion = null
    }


    /*  end of SVM: Create New Category - Choose Categories    */

}