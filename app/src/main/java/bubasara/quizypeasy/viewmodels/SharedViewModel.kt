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

    /*  CreateNewCategory - ChooseCategories    */
    private var newCategory : Category? = null

    //  newCategory setter
    fun setNewCategory(categoryName : String, numberOfQuestions : Int, isChecked : Boolean, listOfQuestions : ArrayList<Question>, imgCategory : Int){
        newCategory = Category(categoryName, numberOfQuestions, isChecked, listOfQuestions, imgCategory)
    }

    //  newCategory getter
    fun getNewCategory() : Category? {
        return newCategory
    }

    /*  end of CreateNewCategory - ChooseCategories    */

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
}