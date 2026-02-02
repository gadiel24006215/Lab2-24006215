package com.example.lab2_24006215.DiceRoller

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "DiceRollerSC"
private const val ANIMATION_ITERATIONS = 15
private const val ANIMATION_DELAY_MS = 80L
private const val MAX_DICE_VALUE = 20
private const val MIN_DICE_VALUE = 1

class DiceRollerSC : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        Log.d(TAG, "onCreate: Activity creada. Inicializando UI...")


        enableEdgeToEdge()

        Log.d(TAG, "onCreate: Edge-to-Edge habilitado")


        setContent {

            MaterialTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    DiceRollerScreen()
                }
            }
        }

        Log.d(TAG, "onCreate: UI de Compose establecida correctamente")
    }




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiceRollerScreen() {

    var diceValue by rememberSaveable { mutableIntStateOf(MIN_DICE_VALUE) }


    var isRolling by remember { mutableStateOf(false) }


    var resultMessage by rememberSaveable { mutableStateOf("Toca el botón para lanzar") }


    val coroutineScope = rememberCoroutineScope()


    fun rollDice() {

        Log.d(TAG, "rollDice: Iniciando lanzamiento del dado")


        coroutineScope.launch {

            isRolling = true
            resultMessage = "Lanzando..."

            Log.d(TAG, "rollDice: Animación iniciada")


            repeat(ANIMATION_ITERATIONS) { iteration ->

                diceValue = (MIN_DICE_VALUE..MAX_DICE_VALUE).random()

                Log.d(TAG, "rollDice: Iteración ${iteration + 1}/$ANIMATION_ITERATIONS, valor temporal: $diceValue")


                delay(ANIMATION_DELAY_MS)
            }

            // Paso 3: Generar el resultado final
            val finalValue = (MIN_DICE_VALUE..MAX_DICE_VALUE).random()
            diceValue = finalValue

            Log.d(TAG, "rollDice: Resultado final: $finalValue")


            resultMessage = when (finalValue) {
                MAX_DICE_VALUE -> "¡CRITICAL HIT! ⚔️"
                MIN_DICE_VALUE -> "¡CRITICAL MISS! 💀"
                else -> "Resultado: $finalValue"
            }


            isRolling = false

            Log.d(TAG, "rollDice: Lanzamiento completado. Mensaje: $resultMessage")
        }
    }


    Scaffold(
        // Barra superior con el título de la app
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "RPG Dice Roller",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { paddingValues ->


        Column(
            modifier = Modifier
                .fillMaxSize()                     // Ocupa todo el espacio disponible
                .padding(paddingValues)            // Respeta el padding del Scaffold
                .padding(horizontal = 24.dp),      // Padding adicional a los lados
            horizontalAlignment = Alignment.CenterHorizontally,  // Centra horizontalmente
            verticalArrangement = Arrangement.Center             // Centra verticalmente
        ) {


            Box(
                modifier = Modifier
                    .size(200.dp),  // Tamaño fijo de 200x200 dp
                contentAlignment = Alignment.Center
            ) {
                // Texto grande mostrando el valor del dado
                Text(
                    text = diceValue.toString(),
                    fontSize = 96.sp,  // Tamaño de fuente grande
                    fontWeight = FontWeight.Bold,
                    // Color condicional basado en el valor
                    color = getDiceValueColor(diceValue, isRolling),
                    textAlign = TextAlign.Center
                )
            }

            // Espacio vertical entre elementos
            Spacer(modifier = Modifier.height(24.dp))

            // -----------------------------------------------------------------
            // SECCIÓN: MENSAJE DE RESULTADO
            // -----------------------------------------------------------------
            Text(
                text = resultMessage,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = if (diceValue == MAX_DICE_VALUE || diceValue == MIN_DICE_VALUE) {
                    FontWeight.Bold
                } else {
                    FontWeight.Normal
                },
                color = getDiceValueColor(diceValue, isRolling),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // -----------------------------------------------------------------
            // SECCIÓN: BOTÓN DE LANZAR
            // -----------------------------------------------------------------
            /**
             * Button es el componente de botón de Material 3.
             *
             * Propiedades importantes:
             * - onClick: Lambda que se ejecuta al hacer clic
             * - enabled: Si es false, el botón está deshabilitado (gris)
             * - colors: Personaliza los colores del botón
             */
            Button(
                onClick = { rollDice() },  // Llama a nuestra función al hacer clic
                enabled = !isRolling,      // Deshabilitado mientras rueda
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.outline
                )
            ) {
                // Contenido del botón: ícono + texto
                Icon(
                    imageVector = Icons.Default.Refresh,  // Ícono de "refresh"
                    contentDescription = "Lanzar dado",   // Accesibilidad
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = if (isRolling) "LANZANDO..." else "LANZAR D20",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texto informativo sobre el dado
            Text(
                text = "Dado de 20 caras (d20)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

}
    private fun getDiceValueColor(value: Int, isRolling: Boolean): Color {
        return when {
            // Durante la animación, usar un color neutral
            isRolling -> Color(0xFF666666)

            // Critical Hit (20) - Color dorado
            value == MAX_DICE_VALUE -> Color(0xFFFFD700)  // Gold

            // Critical Miss (1) - Color rojo
            value == MIN_DICE_VALUE -> Color(0xFFDC143C)  // Crimson

            // Valores normales - Gris oscuro
            else -> Color(0xFF333333)
        }
    }

    @Preview(
        showBackground = true,
        showSystemUi = true,
        name = "Dice Roller Preview"
    )
    @Composable
    fun DiceRollerScreenPreview() {
        MaterialTheme {
            DiceRollerScreen()
        }
    }}
