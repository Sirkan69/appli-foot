package com.example.monclassementfoot.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.monclassementfoot.viewmodels.ChampionshipViewModel
import com.example.monclassementfoot.ui.components.HomeButton
import com.example.monclassementfoot.ui.components.VignetteEquipeCard
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListeEquipesScreen(
    navController: NavHostController,
    viewModel: ChampionshipViewModel,
    nomChampionnat: String
) {
    val equipes = viewModel.getEquipes(nomChampionnat)
    val categorie = viewModel.getCategorie(nomChampionnat)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = nomChampionnat) },
                navigationIcon = { HomeButton(navController) }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate("creer_equipe/$nomChampionnat") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "Créer équipe") },
                text = { Text("Nouvelle équipe") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Bandeau info championnat
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Championnat: $nomChampionnat",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Catégorie: $categorie",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }


            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Liste des équipes",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(equipes) { equipe ->
                    // Interaction source pour détecter clic / pression
                    val interactionSource = remember { MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()
                    val elevation by animateDpAsState(targetValue = if (isPressed) 16.dp else 8.dp)

                    VignetteEquipeCard(
                        nomEquipe = equipe.nom,
                        couleurStart = equipe.couleurPrincipale,
                        couleurEnd = equipe.couleurSecondaire,
                        onClick = {
                            // Naviguer vers CreerEquipeScreen avec le nom du championnat et de l'équipe
                            navController.navigate("creer_equipe/$nomChampionnat/${equipe.nom}")
                        }
                    ) {

                            Text("Coachs: ${equipe.coachs.size}")
                            Text("Joueurs: ${equipe.joueurs.size}")
                        }

                }
            }
        }
    }
}
