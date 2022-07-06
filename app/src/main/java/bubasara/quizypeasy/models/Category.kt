package bubasara.quizypeasy.models

//data class for category with list of questions
data class Category (var categoryName : String, val numberOfQuestions : Int, var isChecked : Boolean, val listOfQuestions : ArrayList<Question>, val imgCategory : Int){
    fun addQuestion(question: Question){
        listOfQuestions.add(question)
    }
}