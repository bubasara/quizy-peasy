package bubasara.quizypeasy.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//data class for question with answers and correct answer
@Entity(tableName = "question")
data class Question (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "question")
    var question : String,
    @ColumnInfo(name = "list_of_answers")
    var listOfAnswers : ArrayList<String>,
    @ColumnInfo(name = "correct_answer")
    var correctAnswer : String)