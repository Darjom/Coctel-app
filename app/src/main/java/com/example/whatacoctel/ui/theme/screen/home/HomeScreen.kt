@file:Suppress("ModifierParameter")
package com.example.whatacoctel.ui.theme.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.whatacoctel.domain.model.CocktailShort
import com.example.whatacoctel.ui.theme.common.NavBar
import coil.compose.rememberAsyncImagePainter

import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import com.example.whatacoctel.ui.theme.Mint

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState


import kotlinx.coroutines.launch

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import coil.compose.AsyncImage



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel,onNavigateToDetail: (String) -> Unit,onNavigateToShake: () -> Unit) {
    val list by viewModel.cocktailList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val selected by viewModel.selected.collectAsState()
    var query by remember { mutableStateOf("") }

    LaunchedEffect(query) {
        delay(500)
        if (query.isBlank()) viewModel.loadAll()
        else viewModel.search(query)
    }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
        ) {
            TextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Buscar cocktail") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(list) { item ->
                    CocktailShortCard(item) {
                        viewModel.loadDetail(item.id)
                    }
                }
            }
        }

        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Text(
                "Error: $error",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }

        if (selected != null) {
            ModalBottomSheet(
                onDismissRequest = {
                    scope.launch { sheetState.hide() }
                    viewModel.clearSelected()
                },
                sheetState = sheetState,
                contentWindowInsets = { BottomSheetDefaults.windowInsets },
            ) {
                selected?.let { cocktail ->
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        AsyncImage(
                            model = cocktail.strDrinkThumb,
                            contentDescription = cocktail.strDrink,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(cocktail.strDrink.orEmpty(), style = MaterialTheme.typography.headlineSmall)
                        Text("Categoría: ${cocktail.strCategory.orEmpty()}", style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(8.dp))
                        Text("Instrucciones:", fontWeight = FontWeight.Bold)
                        Text(cocktail.strInstructions.orEmpty(), style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = {
                            selected?.idDrink?.let { id ->
                                viewModel.clearSelected()
                                onNavigateToDetail(id)
                            }
                        }) {
                            Text("Ver más detalles")
                        }
                    }
                }
            }
            LaunchedEffect(selected) {
                if (selected != null) scope.launch { sheetState.show() }
            }
        }

        NavBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            onShakeClick = onNavigateToShake
        )
    }
}

@Composable
fun CocktailShortCard(cocktail: CocktailShort, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(cocktail.thumbnail),
            contentDescription = cocktail.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = cocktail.name,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

