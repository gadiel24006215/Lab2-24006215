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


    LaunchedEffect(Unit) {
        while (true) {
            currentLight = Light.Red
            delay(2000)

            currentLight = Light.Green
            delay(2000)

            currentLight = Light.Yellow
            delay(1000)
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TrafficLightCircle(
            isActive = currentLight == Light.Red,
            activeColor = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        TrafficLightCircle(
            isActive = currentLight == Light.Yellow,
            activeColor = Color.Yellow
        )

        Spacer(modifier = Modifier.height(16.dp))

        TrafficLightCircle(
            isActive = currentLight == Light.Green,
            activeColor = Color.Green
        )
    }
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
