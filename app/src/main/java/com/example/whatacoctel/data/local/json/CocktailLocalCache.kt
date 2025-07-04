package com.example.whatacoctel.data.local.json

import android.content.Context
import com.example.whatacoctel.domain.model.Cocktail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class CocktailLocalCache(private val context: Context) {
    private val fileName = "cocktails.json"

    fun saveCocktails(cocktails: List<Cocktail>) {
        val json = Gson().toJson(cocktails)
        File(context.filesDir, fileName).writeText(json)
    }

    fun loadCocktails(): List<Cocktail>? {
        val file = File(context.filesDir, fileName)
        return if (file.exists()) {
            val json = file.readText()
            Gson().fromJson(json, object : TypeToken<List<Cocktail>>() {}.type)
        } else null
    }
}