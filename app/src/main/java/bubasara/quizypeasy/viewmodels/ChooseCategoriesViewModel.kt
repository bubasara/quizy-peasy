package bubasara.quizypeasy.viewmodels

import androidx.lifecycle.ViewModel
import bubasara.quizypeasy.models.Category

class ChooseCategoriesViewModel : ViewModel() {
    var listOfCategories = arrayListOf<Category>()
}