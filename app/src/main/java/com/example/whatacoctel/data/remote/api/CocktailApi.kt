package com.example.whatacoctel.data.remote.api

import com.example.whatacoctel.domain.model.Cocktail
import com.example.whatacoctel.domain.model.CocktailListResponse
import com.example.whatacoctel.domain.model.CocktailLookupResponse
import com.example.whatacoctel.domain.model.ListCockatail
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {
    /*@GET("api/json/v1/1/search.php")
    suspend fun searchCocktail(@Query("s") query: String): CocktailResponse*/

    @GET("api/json/v1/1/random.php")
    suspend fun getRandomCocktail(): Cocktail

    @GET("api/json/v1/1/filter.php")
    suspend fun getAllCocktails(@Query("c") category: String = "Cocktail"): CocktailListResponse

    @GET("api/json/v1/1/lookup.php")
    suspend fun lookupCocktail(@Query("i") id: String): CocktailLookupResponse

    @GET("api/json/v1/1/search.php")
    suspend fun searchCocktail(@Query("s") query: String): CocktailListResponse

}