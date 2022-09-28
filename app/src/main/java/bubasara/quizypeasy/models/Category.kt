package bubasara.quizypeasy.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//data class for category with list of questions
@Entity(tableName = "category")
data class Category (
    @ColumnInfo(name = "category_name")
    var categoryName : String,
    @ColumnInfo(name = "number_of_questions")
    var numberOfQuestions : Int,
    @ColumnInfo(name = "is_checked")
    var isChecked : Boolean,
    @ColumnInfo(name = "img_category")
    var imgCategory : Int)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}