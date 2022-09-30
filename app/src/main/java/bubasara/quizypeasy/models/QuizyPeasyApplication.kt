package bubasara.quizypeasy.models

import android.app.Application
import bubasara.quizypeasy.helpers.PreferenceManager

class QuizyPeasyApplication : Application() {
    //  instantiate the database
    val database : QuizyPeasyRoomDatabase by lazy { QuizyPeasyRoomDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()

        // init pref manager
        PreferenceManager.preferenceManagerInit(this)
    }
}