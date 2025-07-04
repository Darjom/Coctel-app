package com.example.whatacoctel.ui.theme.screen.home


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.whatacoctel.data.remote.api.RetrofitInstance
import com.example.whatacoctel.domain.model.Cocktail
import com.example.whatacoctel.domain.model.ListCockatail
import com.example.whatacoctel.domain.repository.CocktailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(): ViewModel() {

    //private val _cocktails = mutableStateOf<List<Cocktail>>(emptyList())
    //val cocktail = RetrofitInstance.service.getRandomCocktail()

    private val _cocktail = MutableStateFlow<Cocktail?>(null)
    val cocktail: StateFlow<Cocktail?> = _cocktail

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init{
        random()
    }


    fun random() {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.service.getRandomCocktail()
                _cocktail.value = result
            }catch (e: Exception) {
                _error.value = e.message
            }finally{
                _isLoading.value = false
            }
        }
    }
}




