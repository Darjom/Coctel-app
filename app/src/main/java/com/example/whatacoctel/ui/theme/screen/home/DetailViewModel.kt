package com.example.whatacoctel.ui.theme.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatacoctel.data.remote.api.RetrofitInstance
import com.example.whatacoctel.domain.model.Cocktail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(cocktailId: String): ViewModel() {
    val cocktail = MutableStateFlow<Cocktail?>(null)
    val isLoading = MutableStateFlow(false)
    val error = MutableStateFlow<String?>(null)

    init {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val resp = RetrofitInstance.service.lookupCocktail(cocktailId)
                cocktail.value = resp.drinks.firstOrNull()
            } catch (e: Exception) {
                error.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }
}
