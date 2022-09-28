package bubasara.quizypeasy.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//  the Database class provides the app with instances of the DAOs defined
@Database(entities = [Category::class, Question::class], version = 1, exportSchema = false)
abstract class QuizyPeasyRoomDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun questionDao(): QuestionDao

    /*  allows access to the methods for creating or getting the db
        using the class name as the qualifier   */
    companion object {
        //  singleton because we need only one instance of the db
        @Volatile
        private var INSTANCE: QuizyPeasyRoomDatabase? = null

        fun getDatabase(context: Context): QuizyPeasyRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizyPeasyRoomDatabase::class.java,
                    "quizy_peasy_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                //return instance
                instance
            }
        }
    }
}