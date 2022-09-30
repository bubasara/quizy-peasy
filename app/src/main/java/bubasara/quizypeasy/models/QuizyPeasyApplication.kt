package bubasara.quizypeasy.models

import android.app.Application
import bubasara.quizypeasy.helpers.PreferenceManager
import bubasara.quizypeasy.models.QuizyPeasyRoomDatabase
import bubasara.quizypeasy.utils.getJsonDataFromAsset
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuizyPeasyApplication : Application() {
    //  instantiate the database
    val database : QuizyPeasyRoomDatabase by lazy { QuizyPeasyRoomDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()

        // init pref manager
        PreferenceManager.preferenceManagerInit(this)
    }
}