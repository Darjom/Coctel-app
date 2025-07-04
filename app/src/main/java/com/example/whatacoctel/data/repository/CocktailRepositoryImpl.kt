package com.example.whatacoctel.data.repository


import android.content.Context
import com.example.whatacoctel.data.local.json.CocktailLocalCache
import com.example.whatacoctel.data.remote.api.CocktailApi
//import com.example.whatacoctel.data.remote.dto.toDomain
import com.example.whatacoctel.domain.model.Cocktail
import com.example.whatacoctel.domain.repository.CocktailRepository

/*class CocktailRepositoryImpl(
    private val api: CocktailApi,
    private val localCache: CocktailLocalCache
): CocktailRepository {

    override suspend fun getCocktails(query: String): List<Cocktail> {
        return try {
            val response = api.searchCocktail(query)
            val cocktails = response.drinks?.map { it.toDomain() } ?: emptyList()
            localCache.saveCocktails(cocktails)
            cocktails
        } catch (e: Exception) {
            localCache.loadCocktails() ?: emptyList()
        }
    }
}*/