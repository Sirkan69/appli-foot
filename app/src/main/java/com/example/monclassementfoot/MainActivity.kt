package com.example.monpremiereapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import com.example.monpremiereapp.ui.CreateChampionshipScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CreateChampionshipScreen { name, ageCategory ->
                    Toast.makeText(
                        this,
                        "Champ créé : $name ($ageCategory)",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
