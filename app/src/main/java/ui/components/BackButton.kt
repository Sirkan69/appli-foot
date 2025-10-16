package com.example.monclassementfoot.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.navigation.NavHostController

/**
 * Composant réutilisable pour un bouton "retour"
 *
 * @param navController Le NavController pour naviguer en arrière
 * @param onClickAction Action personnalisée à effectuer (facultatif)
 * @param icon Icône à afficher (par défaut flèche retour)
 */
@Composable
fun BackButton(
    navController: NavHostController,
    onClickAction: (() -> Unit)? = null,
    icon: ImageVector = Icons.Default.ArrowBack,
    contentDescription: String = "Retour"
) {
    IconButton(
        onClick = {
            onClickAction?.invoke() ?: navController.popBackStack() // si pas d'action, fait un popBackStack
        }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}
