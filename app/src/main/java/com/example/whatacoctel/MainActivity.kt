package com.example.whatacoctel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.whatacoctel.data.local.json.CocktailLocalCache
import com.example.whatacoctel.data.remote.api.CocktailApi
//import com.example.whatacoctel.data.repository.CocktailRepositoryImpl
import com.example.whatacoctel.domain.repository.CocktailRepository
import com.example.whatacoctel.ui.theme.WhatACoctelTheme
import com.example.whatacoctel.ui.theme.screen.home.CocktailScreen
import com.example.whatacoctel.ui.theme.screen.home.HomeViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CocktailScreen(HomeViewModel())
        }
    }
}

