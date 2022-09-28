package bubasara.quizypeasy.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//data class for question with answers and correct answer
@Entity(tableName = "question")
data class Question (
    @ColumnInfo(name = "question")
    var question : String,
    /*@ColumnInfo(name = "list_of_answers")
    var listOfAnswers : ArrayList<String>,*/
    @ColumnInfo(name = "answer_a")
    var answerA : String,
    @ColumnInfo(name = "answer_b")
    var answerB : String,
    @ColumnInfo(name = "answer_c")
    var answerC : String,
    @ColumnInfo(name = "answer_d")
    var answerD : String,
    @ColumnInfo(name = "correct_answer")
    var correctAnswer : String,
    @ColumnInfo(name = "category")
    var category: Int)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}