package ru.sashel007.quitsmoking.util

import android.content.Context
import com.google.gson.Gson
import ru.sashel007.quitsmoking.data.repository.dto.CancelSmokingTips
import ru.sashel007.quitsmoking.data.repository.dto.Fact
import ru.sashel007.quitsmoking.data.repository.dto.FaqItem
import ru.sashel007.quitsmoking.data.repository.dto.Myth
import ru.sashel007.quitsmoking.data.repository.dto.Tip
import ru.sashel007.quitsmoking.data.repository.dto.mappers.Disclaimer
import java.io.IOException


fun getTipsForSettingsFromJson(context: Context): String {
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

fun getFaqItemsFromJson(context: Context): List<FaqItem> {
    val jsonString: String = try {
        context.assets.open("faq_items.json").bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        "Error loading file"
    }

    return try {
        val gson = Gson()
        val faqList = gson.fromJson(jsonString, Array<FaqItem>::class.java)
        faqList.toList()
    } catch (e: Exception) {
        "Error parsing JSON"
        listOf(FaqItem("error", "error"))
    }
}

fun getTipsFromJson(context: Context): List<Tip> {
    val jsonString: String = try {
        context.assets.open("tips.json").bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        "Error loading file"
    }

    return try {
        val gson = Gson()
        val tipList = gson.fromJson(jsonString, Array<Tip>::class.java)
        tipList.toList()
    } catch (e: Exception) {
        "Error parsing JSON"
        listOf(Tip(1000, "error", "error"))
    }
}

fun getFactsFromJson(context: Context): List<Fact> {
    val jsonString: String = try {
        context.assets.open("facts.json").bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        "Error loading file"
    }

    return try {
        val gson = Gson()
        val factList = gson.fromJson(jsonString, Array<Fact>::class.java)
        factList.toList()
    } catch (e: Exception) {
        "Error parsing JSON"
        listOf(Fact(1000, "error", "error"))
    }
}

fun getMythsFromJson(context: Context): List<Myth> {
    val jsonString: String = try {
        context.assets.open("myths.json").bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        "Error loading file"
    }

    return try {
        val gson = Gson()
        val mythList = gson.fromJson(jsonString, Array<Myth>::class.java)
        mythList.toList()
    } catch (e: Exception) {
        "Error parsing JSON"
        listOf(Myth(0, "error", "error"))
    }
}

fun loadDisclaimer(context: Context): Disclaimer? {
    val jsonString: String = try {
        context.assets.open("disclaimer.json").bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        "Error loading file"
    }
    return try {
        val gson = Gson()
        gson.fromJson(jsonString, Disclaimer::class.java)
    } catch (e: Exception) {
        "error parsing json"
        Disclaimer("",listOf(""))
    }
}