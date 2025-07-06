package com.example.whatacoctel.domain.model

import com.google.gson.annotations.SerializedName

data class CocktailShort(
    @SerializedName("idDrink") val id: String,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strDrinkThumb") val thumbnail: String
)

data class CocktailListResponse(
    @SerializedName("drinks") val drinks: List<CocktailShort>
)

data class CocktailLookupResponse(
    @SerializedName("drinks")
    val drinks: List<Cocktail>
)

