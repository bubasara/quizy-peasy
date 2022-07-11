package bubasara.quizypeasy.models

import androidx.room.ColumnInfo
import androidx.room.Entity

//data class for category with questions
@Entity(tableName = "category_questions")
data class CategoryQuestions (
    @ColumnInfo(name = "id_category")
    var categoryId : Int,
    @ColumnInfo(name = "id_question")
    var questionId : Int)