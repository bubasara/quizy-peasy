package bubasara.quizypeasy.viewmodels

import androidx.lifecycle.*
import bubasara.quizypeasy.models.Category
import bubasara.quizypeasy.models.CategoryDao
import bubasara.quizypeasy.models.CategoryWithQuestions
import bubasara.quizypeasy.models.Question
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class CategoryViewModel(private val categoryDao : CategoryDao) : ViewModel() {

    val allCategories : LiveData<List<Category>> = categoryDao.getCategories().asLiveData()

    var allCategoriesMutable : MutableLiveData<MutableList<Category>> = MutableLiveData(allCategories.value?.toMutableList())


    lateinit var categoriesWithQuestions : List<CategoryWithQuestions>

    /*  to interact with the database off the main thread,
        start a coroutine and call the DAO method within it
     */

    fun retrieveCategory(id : Int) : LiveData<Category> {
        return categoryDao.getCategory(id).asLiveData()
    }

    fun retrieveAllCategories() : LiveData<List<Category>> {
        return allCategories
    }

    fun retrieveAllCategoriesRightMeow() : LiveData<List<Category>>
    {
        return categoryDao.getCategories().asLiveData()
    }

    private fun insertCategory(category: Category){
        viewModelScope.launch {
            categoryDao.insert(category)
        }
    }


    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            categoryDao.delete(category)
        }
    }

    fun isEntryValid(categoryName : String, listOfQuestions : List<Question>): Boolean {
        if (categoryName.isBlank() || listOfQuestions.isEmpty()) {
            return false
        }
        return true
    }

    fun addNewCategory(categoryName: String, numberOfQuestions : Int,
                       isChecked : Boolean, imgCategory : Int){
        val newCategory = getNewCategoryEntry(categoryName, numberOfQuestions, isChecked, imgCategory)
        insertCategory(newCategory)
    }

    fun addNewCategoryObject(category: Category){
        val newCategory = getNewCategoryEntry(category.categoryName, category.numberOfQuestions,
            category.isChecked, category.imgCategory)
        insertCategory(newCategory)
    }

    private fun getNewCategoryEntry(
        categoryName : String,
        numberOfQuestions : Int,
        isChecked : Boolean,
        imgCategory : Int
    ) : Category {
        return Category(
            categoryName = categoryName,
            numberOfQuestions = numberOfQuestions,
            isChecked = isChecked,
            imgCategory = imgCategory
        )
    }

    /*  for future upgrades, atm updating category is not available  */
    private fun updateCategory(category: Category) {
        viewModelScope.launch {
            categoryDao.update(category)
        }
    }

    private fun getUpdatedCategoryEntry(
        categoryName : String,
        numberOfQuestions : Int,
        isChecked : Boolean,
        imgCategory : Int
    ): Category {
        return Category(
            categoryName = categoryName,
            numberOfQuestions = numberOfQuestions,
            isChecked = isChecked,
            imgCategory = imgCategory
        )
    }
    /* end of: for future upgrades, atm updating category is not available   */
}

class CategoryViewModelFactory(private val categoryDao: CategoryDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(CategoryViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(categoryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
