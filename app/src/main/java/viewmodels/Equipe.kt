package com.example.monclassementfoot.viewmodels
import androidx.compose.ui.graphics.Color

/**
 * Modèle représentant une équipe de football.
 * Contient les infos de base, les coachs, les joueurs et les couleurs de l'équipe.
 */
data class Equipe(
    val nom: String,
    val coachs: List<Pair<String, String>>,
    val joueurs: List<Triple<String, List<String>, String>>,
    val couleurPrincipale: Color = Color.White,
    val couleurSecondaire: Color = Color.White
)
