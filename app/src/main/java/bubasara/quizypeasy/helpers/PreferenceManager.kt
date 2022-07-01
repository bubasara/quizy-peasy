package bubasara.quizypeasy.helpers

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity
import bubasara.quizypeasy.models.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

// shared preference manager used to manage data from shared preferences
object PreferenceManager {

    //  singleton, alive while the app is alive
    var preferenceManagerSingleton : SharedPreferences? = null

    fun preferenceManagerInit (context: Context) {
        if(preferenceManagerSingleton == null){
            preferenceManagerSingleton = context.getSharedPreferences("MyPref", FragmentActivity.MODE_PRIVATE)
        }
    }

    //  first app startup
    fun getBoolean(name: String, value: Boolean) : Boolean?{
        return preferenceManagerSingleton?.getBoolean(name, value)
    }

    fun setBoolean(name: String, value: Boolean){
        val sharedPreferencesEditor : SharedPreferences.Editor = preferenceManagerSingleton!!.edit()
        sharedPreferencesEditor.putBoolean(name, value)
        sharedPreferencesEditor.apply()
    }

    //  listOfCategories getter
    fun getListOfCategories() : ArrayList<Category>{
        val json = preferenceManagerSingleton?.getString("listOfCategories", null)
        val type: Type = object : TypeToken<ArrayList<Category?>?>() {}.type
        return if (Gson().fromJson<ArrayList<Category>>(json, type) == null) {
            ArrayList()
        } else Gson().fromJson(json, type)
    }

    //  listOfCategories setter
    fun setListOfCategories(listOfCategories : ArrayList<Category>){
        val preferenceManagerEditor = preferenceManagerSingleton?.edit()
        val json = Gson().toJson(listOfCategories)
        preferenceManagerEditor?.putString("listOfCategories", json)?.apply()
    }
}