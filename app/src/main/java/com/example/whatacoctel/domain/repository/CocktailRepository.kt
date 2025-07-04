package com.example.whatacoctel.domain.repository

import com.example.whatacoctel.domain.model.Cocktail

interface CocktailRepository {
    suspend fun getCocktails(query: String): List<Cocktail>
}