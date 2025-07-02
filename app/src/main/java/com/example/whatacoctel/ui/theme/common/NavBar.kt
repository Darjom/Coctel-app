package com.example.whatacoctel.ui.theme.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NavBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(Color.DarkGray)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Inicio", color = Color.White)
        Text("Populares", color = Color.White)
        Text("Shake it", color = Color.White)
    }
}
