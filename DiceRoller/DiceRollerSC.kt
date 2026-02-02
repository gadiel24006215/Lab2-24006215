package com.example.lab2_24006215.DiceRoller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DiceRollerSC : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CharacterSheetScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterSheetScreen() {


    var vitality by rememberSaveable { mutableIntStateOf(10) }
    var dexterity by rememberSaveable { mutableIntStateOf(10) }
    var wisdom by rememberSaveable { mutableIntStateOf(10) }


    val totalScore = vitality + dexterity + wisdom

    val feedbackText = when {
        totalScore < 30 -> "Re-roll recommended!"
        totalScore >= 50 -> "Godlike!"
        else -> ""
    }

    val feedbackColor = when {
        totalScore < 30 -> Color.Red
        totalScore >= 50 -> Color(0xFFFFD700) // Dorado
        else -> Color.Unspecified
    }


}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CharacterSheetPreview() {
    MaterialTheme {
        CharacterSheetScreen()
    }
}

