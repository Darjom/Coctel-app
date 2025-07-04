package com.example.whatacoctel.ui.theme.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.whatacoctel.ui.theme.common.NavBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.whatacoctel.domain.model.Cocktail
import com.example.whatacoctel.domain.repository.CocktailRepository
/*

@Composable
fun HomeScreen(repository: CocktailRepository,) {
    var query by remember { mutableStateOf("") }
    val factory = remember { HomeViewModelFactory(repository) }
    val viewModel: HomeViewModel = viewModel(factory = factory)

    val cocktails by viewModel.cocktails.collectAsState()

    LaunchedEffect(query) {
        viewModel.search(query)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            // Input de Buscador
            TextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Buscar cocktail") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 5.dp)
            )

            // Grid con 2 columnas
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cocktails) { cocktail ->
                    CocktailCard(cocktail)
                }
            }
        }

        // Navbar fijo en la parte inferior
        NavBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun CocktailCard(cocktail: Cocktail) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = cocktail.name,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "ID: ${cocktail.id}",
                color = Color.LightGray,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
} */


@Composable
fun CocktailScreen(viewModel: HomeViewModel) {
    val cocktail by viewModel.cocktail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        error != null -> {
            Text(
                text = "Error: $error",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }
        cocktail != null -> {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "ya veremos que viene aqui",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))



                Spacer(modifier = Modifier.height(12.dp))

                Text("Ingredientes:", fontWeight = FontWeight.Bold)
                cocktail!!.strIngredient1?.let {
                    Text("- ${cocktail!!.strMeasure1 ?: ""} $it")
                }
                cocktail!!.strIngredient2?.let {
                    Text("- ${cocktail!!.strMeasure2 ?: ""} $it")
                }
                cocktail!!.strIngredient3?.let {
                    Text("- ${cocktail!!.strMeasure3 ?: ""} $it")
                }
                cocktail!!.strIngredient4?.let {
                    Text("- ${cocktail!!.strMeasure4 ?: ""} $it")
                }
            }
        }
        else -> {
            Text(
                text = "Sin datos cargados.",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}