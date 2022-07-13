package bubasara.quizypeasy.models

import android.app.Application
import bubasara.quizypeasy.models.QuizyPeasyRoomDatabase

class QuizyPeasyApplication : Application() {
    //  instantiate the database
    val database : QuizyPeasyRoomDatabase by lazy { QuizyPeasyRoomDatabase.getDatabase(this) }
}