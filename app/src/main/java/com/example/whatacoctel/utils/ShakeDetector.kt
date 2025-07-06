package com.example.whatacoctel.utils


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlin.math.abs

@Composable
fun rememberShakeDetector(onShake: () -> Unit) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val listener = object : SensorEventListener {
            override fun onSensorChanged(e: SensorEvent) {
                val total = abs(e.values[0]) + abs(e.values[1]) + abs(e.values[2])
                if (total > 30f) onShake()  // ajusta sensibilidad aquí según pruebas :contentReference[oaicite:3]{index=3}
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
        }
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        onDispose { sensorManager.unregisterListener(listener) }
    }
}
