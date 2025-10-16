package com.example.monclassementfoot.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

/**
 * Composable réutilisable : bouton Home
 * Affiche une petite icône de maison
 * Quand on clique dessus, navigue vers l'écran d'accueil ("accueil")
 */
@Composable
fun HomeButton(navController: NavController) {
    IconButton(
        onClick = {
            // Navigate vers l'écran d'accueil
            navController.navigate("accueil") {
                // Supprime la pile de navigation pour éviter de revenir en arrière
                popUpTo("accueil") { inclusive = true }
            }
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Home,
            contentDescription = "Accueil"
        )
    }
}
