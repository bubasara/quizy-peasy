package bubasara.quizypeasy.models

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryQuestionsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(id_category : Int, id_question : Int)

    @Update
    suspend fun update(id_category : Int, id_question : Int)

    @Delete
    suspend fun deleteCategory(id_category : Int)

    @Delete
    suspend fun deleteQuestion(id_question : Int)

    @Query("SELECT * from category_questions WHERE id_category = :id_category")
    fun getCategoryWithQuestions(id: Int) : Flow<CategoryQuestions>

    @Query("SELECT * from category_questions ORDER BY id ASC")
    fun getCategoriesWithQuestions() : Flow<CategoryQuestions>

}