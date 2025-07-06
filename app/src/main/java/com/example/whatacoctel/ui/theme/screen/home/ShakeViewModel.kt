package com.example.whatacoctel.ui.theme.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatacoctel.data.remote.api.RetrofitInstance
import com.example.whatacoctel.domain.model.Cocktail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShakeViewModel : androidx.lifecycle.ViewModel() {
    private val _cocktail = MutableStateFlow<Cocktail?>(null)
    val cocktail: StateFlow<Cocktail?> = _cocktail
    val isLoading = MutableStateFlow(false)
    val error = MutableStateFlow<String?>(null)

    fun fetchRandom() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = RetrofitInstance.service.getRandomCocktail()
                val fetched = response.drinks.firstOrNull()
                _cocktail.value = fetched
                error.value = null
            } catch (e: Exception) {
                error.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }
}