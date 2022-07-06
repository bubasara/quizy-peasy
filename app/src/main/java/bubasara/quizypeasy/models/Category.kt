package bubasara.quizypeasy.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//data class for category with list of questions
@Entity(tableName = "category")
data class Category (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "category_name")
    var categoryName : String,
    @ColumnInfo(name = "number_of_questions")
    val numberOfQuestions : Int,
    @ColumnInfo(name = "is_checked")
    var isChecked : Boolean,
    @ColumnInfo(name = "list_of_questions")
    val listOfQuestions : ArrayList<Question>,
    @ColumnInfo(name = "img_category")
    val imgCategory : Int)
{
    fun addQuestion(question: Question){
        listOfQuestions.add(question)
    }
}