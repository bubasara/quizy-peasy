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

        // init pref managera, citanje iz asseta i uspis u pref ako je pref prazan
        PreferenceManager.preferenceManagerInit(this)
        /*if(PreferenceManager.getListOfCategories().isEmpty())
            setListOfCategoriesFromJson(this)*/
    }


/*    private fun setListOfCategoriesFromJson(application: Application){
        val json = getJsonDataFromAsset(application, "data.json")
        val list = object : TypeToken<List<Category>>() {}.type
        PreferenceManager.setListOfCategories(Gson().fromJson(json, list))
    }*/
}