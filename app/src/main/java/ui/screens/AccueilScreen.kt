package com.example.monclassementfoot.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.monclassementfoot.viewmodels.ChampionshipViewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.monclassementfoot.ui.components.VignetteCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccueilScreen(viewModel: ChampionshipViewModel, navController: NavController) {
    val championnats = viewModel.getChampionnats()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate("create_championship") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "Créer un championnat") },
                text = { Text("Créer mon championnat") }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // 1️⃣ Titre et sous-texte
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "My Personal League",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Bienvenue sur My Personal League, vous pouvez dès maintenant créer votre propre championnat",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = 20.sp
                    )
                }
            }

            // 2️⃣ "Mes Championnats"
            item {
                Text(
                    text = "Mes Championnats",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // 3️⃣ Liste des championnats avec VignetteCard
            items(championnats) { championnat ->
                val categorie = viewModel.getCategorie(championnat)

                VignetteCard(
                    title = championnat,
                    subtitleRight = categorie,  // catégorie à droite du bandeau
                    onClick = { navController.navigate("liste_equipes/$championnat") }
                ) {
                    Text("Nombre d'équipes: ${viewModel.getEquipes(championnat).size}")
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}