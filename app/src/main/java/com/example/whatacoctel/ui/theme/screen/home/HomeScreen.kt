@file:Suppress("ModifierParameter")
package com.example.whatacoctel.ui.theme.screen.home


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.whatacoctel.domain.model.CocktailShort
import com.example.whatacoctel.ui.theme.common.NavBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel,onNavigateToDetail: (String) -> Unit,onNavigateToShake: () -> Unit,onNavigateToHome: () -> Unit) {
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
            .padding(top = 50.dp)
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
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
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
                                .height(200.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(cocktail.strDrink.orEmpty(), style = MaterialTheme.typography.headlineSmall)
                        Text("Categoría: ${cocktail.strCategory.orEmpty()}", style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(8.dp))
                        Text("Instrucciones:", fontWeight = FontWeight.Bold)
                        Text(cocktail.strInstructionsES.orEmpty(), style = MaterialTheme.typography.bodySmall)
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
            onShakeClick = onNavigateToShake,
            onHomeClick = onNavigateToHome
        )
    }
}


@Composable
fun CocktailShortCard(cocktail: CocktailShort, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, Color.Gray),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(cocktail.thumbnail),
                contentDescription = cocktail.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = cocktail.name,
                //fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}


