package com.example.whatacoctel.ui.theme.screen.home

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.sqrt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShakeScreen(onRandomFetched: (String) -> Unit) {
    val viewModel = remember { ShakeViewModel() }
    val cocktail by viewModel.cocktail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val gX = event.values[0]
                val gY = event.values[1]
                val gZ = event.values[2]
                val gForce = kotlin.math.sqrt(gX*gX + gY*gY + gZ*gZ) / SensorManager.GRAVITY_EARTH
                if (gForce > 2.7f) {
                    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                    vibrator?.let {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            it.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                        } else {
                            @Suppress("DEPRECATION")
                            it.vibrate(200)
                        }
                    }
                    viewModel.fetchRandom()
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
        }
        accel?.let {
            sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI)
            onDispose { sensorManager.unregisterListener(listener) }
        } ?: onDispose { /* sin acelerómetro */ }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            isLoading -> CircularProgressIndicator()
            error != null -> Text("Error: $error", color = MaterialTheme.colorScheme.error)
            cocktail == null -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "¡Sacude el móvil!",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Deja que el azar elija un cóctel para ti.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
            else -> {
                LaunchedEffect(cocktail) { onRandomFetched(cocktail!!.idDrink.orEmpty()) }
            }
        }
    }

}