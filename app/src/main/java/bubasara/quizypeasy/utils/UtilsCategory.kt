package bubasara.quizypeasy.utils

import android.app.Application
import android.content.Context
import bubasara.quizypeasy.models.CategoryWithQuestions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

fun getJsonDataFromAsset(context : Context, fileName : String) : String? {
    val json : String
    try {
        json = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException : IOException) {
        ioException.printStackTrace()
        return null
    }
    return json
}

 fun getListOfCategoriesFromJson(context : Context) : List<CategoryWithQuestions>{
    val json = getJsonDataFromAsset(context, "data.json")
    val list  = object : TypeToken<List<CategoryWithQuestions>>() {}.type
    return Gson().fromJson(json, list)
}