package bubasara.quizypeasy.models

//data class for question with answers and correct answer
data class Question (var question : String, var listOfAnswers : ArrayList<String>, var correctAnswer : Int)