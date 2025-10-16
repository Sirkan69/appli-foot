package com.example.monclassementfoot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.monclassementfoot.ui.navigation.NavGraph
import androidx.navigation.compose.rememberNavController
import com.example.monclassementfoot.viewmodels.ChampionshipViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonClassementFootApp()
        }
    }
}

@Composable
fun MonClassementFootApp() {
    val navController = rememberNavController()
    val viewModel = ChampionshipViewModel()

    Surface(color = MaterialTheme.colorScheme.background) {
        NavGraph(
            navController = navController,
            viewModel = viewModel
        )
    }
}
