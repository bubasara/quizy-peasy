package bubasara.quizypeasy.models

//data class has all getters and setters, as well as a constructor
data class Category (var categoryName : String, val numberOfQuestions : Int, var isChecked : Boolean, val listOfQuestions : ArrayList<String>, val imgCategory : Int)