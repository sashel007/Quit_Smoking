package ru.sashel007.quitsmoking.util

import android.content.Context
import com.google.gson.Gson
import ru.sashel007.quitsmoking.data.repository.dto.CancelSmokingTips
import java.io.IOException

fun getTipsFromJson(context: Context): String {
    val jsonString: String = try {
        context.assets.open("cancel_smoking_tips.json").bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return "Error loading file"
    }

    return try {
        val gson = Gson()
        val tipsList = gson.fromJson(jsonString, Array<CancelSmokingTips>::class.java)
        if (tipsList.isNotEmpty()) {
            tipsList[0].text
        } else {
            "No data available"
        }
    } catch (e: Exception) {
        "Error parsing JSON"
    }
}