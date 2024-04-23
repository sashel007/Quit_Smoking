package ru.sashel007.quitsmoking.util

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.sashel007.quitsmoking.data.db.entity.AchievementData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AchievementSerialization @Inject constructor(private val context: Context) {
    data class AchievementJson(
        val name: String,
        val description: String,
        val imageUri: String,
        val isUnlocked: Boolean,
        val duration: Int,
        val progressLine: Int
    )

    fun loadAchievementsFromJson(): List<AchievementData> {
        Log.d("JSON Loading", "внутрь метода loadAchievementsFromJson")
        try {
            val jsonString = context.assets.open("achievements.json").bufferedReader().use { it.readText() }
            Log.d("JSON Loading", "Data loaded: $jsonString")
            val type = object : TypeToken<List<AchievementJson>>() {}.type
            val achievementsJson: List<AchievementJson> = Gson().fromJson(jsonString, type)
            return achievementsJson.map { json ->
                AchievementData(
                    name = json.name,
                    description = json.description,
                    imageUri = json.imageUri,
                    isUnlocked = json.isUnlocked,
                    duration = json.duration,
                    progressLine = json.progressLine
                )
            }
        } catch (e: Exception) {
            Log.e("JSON Loading", "Failed to load or parse achievements.json", e)
            return emptyList()
        }
    }

}