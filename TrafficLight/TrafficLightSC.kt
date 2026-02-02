package com.example.lab2_24006215.TrafficLight


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


enum class Light {
    Red, Yellow, Green
}

class TrafficLightSC : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    TrafficLightScreen()
                }
            }
        }
    }
}

@Composable
fun TrafficLightScreen() {


    var currentLight by remember { mutableStateOf(Light.Red) }



}

@Composable
fun TrafficLightCircle(
    isActive: Boolean,
    activeColor: Color
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(
                if (isActive) activeColor else Color.DarkGray
            )
    )
}
