package bubasara.quizypeasy.models

data class CategoryWithQuestions(var id: Int, var categoryName : String, var numberOfQuestions : Int,
                                 var isChecked : Boolean, var imgCategory : Int,
                                 var listOfQuestions: List<Question>)