package bubasara.quizypeasy.models

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(question: Question)

    @Update
    suspend fun update(question: Question)

    @Delete
    suspend fun delete(question: Question)

    @Query("SELECT * from question WHERE id = :id")
    fun getQuestion(id: Int) : Flow<Question>

    @Query("SELECT * from question ORDER BY id ASC")
    fun getQuestions() : Flow<List<Question>>

    @Query("SELECT * from question WHERE category = :category")
    fun getQuestionsFromCategory() : Flow<List<Question>>

}