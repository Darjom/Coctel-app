package com.example.whatacoctel.ui.theme.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    cocktailId: String,
    onBack: () -> Unit
) {
    // Instanciamos el ViewModel
    val viewModel = remember { DetailViewModel(cocktailId) }

    val cocktail by viewModel.cocktail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(cocktail?.strDrink ?: "Detalle del Cóctel") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Text(
                    text = "Error: $error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
            cocktail != null -> {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        model = cocktail!!.strDrinkThumb,
                        contentDescription = cocktail!!.strDrink,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = cocktail!!.strDrink ?: "",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Categoría: ${cocktail!!.strCategory.orEmpty()}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("Instrucciones:", fontWeight = FontWeight.Bold)
                    Text(cocktail!!.strInstructionsES.orEmpty(), style = MaterialTheme.typography.bodySmall)
                    Spacer(Modifier.height(16.dp))

                    // Mostrar algunos ingredientes
                    listOfNotNull(
                        cocktail!!.strIngredient1?.let { it to cocktail!!.strMeasure1 },
                        cocktail!!.strIngredient2?.let { it to cocktail!!.strMeasure2 },
                        cocktail!!.strIngredient3?.let { it to cocktail!!.strMeasure3 },
                        cocktail!!.strIngredient4?.let { it to cocktail!!.strMeasure4 },
                        cocktail!!.strIngredient5?.let { it to cocktail!!.strMeasure5 },
                        cocktail!!.strIngredient6?.let { it to cocktail!!.strMeasure6 },
                        cocktail!!.strIngredient7?.let { it to cocktail!!.strMeasure7 },
                        cocktail!!.strIngredient8?.let { it to cocktail!!.strMeasure8 },
                        cocktail!!.strIngredient9?.let { it to cocktail!!.strMeasure9 }
                    ).forEach {
                        Text("- $it")
                    }
                }
            }
        }
    }
}
