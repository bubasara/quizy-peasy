package bubasara.quizypeasy.models

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: Category)

    @Update
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)

    @Query("SELECT * from category WHERE id = :id")
    fun getCategory(id: Int) : Flow<Category>

    @Query("SELECT * from category ORDER BY id ASC")
    fun getCategories() : Flow<Category>

}