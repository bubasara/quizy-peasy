package bubasara.quizypeasy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import bubasara.quizypeasy.models.Category
import bubasara.quizypeasy.utils.getJsonDataFromAsset
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import bubasara.quizypeasy.models.Question as Question

//  shared view model among all fragments, separated by relations (fragment1 - fragment2)
class SharedViewModel(application : Application) : AndroidViewModel(application) {

    /*  SVM: CreateNewCategory - ChooseCategories    */
    private var newCategory : Category? = null

    //  newCategory setter
    fun setNewCategory(categoryName : String, numberOfQuestions : Int, isChecked : Boolean, imgCategory : Int){
    newCategory = Category(categoryName, numberOfQuestions, isChecked, imgCategory)

    }

    //  newCategory getter
    fun getNewCategory() : Category? {
        return newCategory
    }

    fun resetNewCategory(){
        newCategory = null
    }

    //  live data for deleting category
    var categoryDeleteLiveDataBoolean : MutableLiveData<Boolean> = MutableLiveData()

    /*  end of SVM: CreateNewCategory - ChooseCategories    */
    /*  SVM: Choose Categories - Gameplay  */

    //  list of IDs of categories which are checked for play
    var listOfCheckedCategories : ArrayList<Int> = arrayListOf()

    fun addToCheckedCategories(categoryId : Int) {
        listOfCheckedCategories.add(categoryId)
    }

    fun removeFromCheckedCategories(categoryId: Int){
        listOfCheckedCategories.remove(categoryId)
    }

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

    /*  SVM: Create New Category - EditDeleteQuestion    */
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

    var questionDeleteLiveDataBoolean: MutableLiveData<Boolean> = MutableLiveData()
    var questionEditLiveDataBoolean: MutableLiveData<Boolean> = MutableLiveData()

    /*  end of SVM: Create New Category - EditDeleteQuestion    */

    /*  SVM: Create New Category - Create New Question    */
    fun addQuestionToCategory(question: Question){
        //TODO
        //newCategory?.listOfQuestions?.add(question)
        newQuestion?.category = newCategory!!.id
    }
    /*  end of SVM: Create New Category - Create New Question    */

}