package com.example.monclassementfoot.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.monclassementfoot.viewmodels.ChampionshipViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateChampionshipScreen(
    navController: NavHostController,
    viewModel: ChampionshipViewModel
) {
    var nomChamp by remember { mutableStateOf("") }

    // Liste des catégories d'âge
    val categoriesAge = listOf(
        "U6/U7", "U8/U9", "U10/U11", "U12/U13",
        "U14/U15", "U16/U17", "U18/U19", "U20",
        "Séniors", "Vétérans", "Loisirs"
    )

    var categorieSelectionnee by remember { mutableStateOf(categoriesAge.first()) }
    var menuOuvert by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        OutlinedTextField(
            value = nomChamp,
            onValueChange = { nomChamp = it },
            label = { Text("Nom du championnat") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Menu déroulant pour la catégorie d'âge
        ExposedDropdownMenuBox(
            expanded = menuOuvert,
            onExpandedChange = { menuOuvert = !menuOuvert }
        ) {
            OutlinedTextField(
                value = categorieSelectionnee,
                onValueChange = {},
                readOnly = true,
                label = { Text("Catégorie d'âge") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuOuvert) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = menuOuvert,
                onDismissRequest = { menuOuvert = false }
            ) {
                categoriesAge.forEach { categorie ->
                    DropdownMenuItem(
                        text = { Text(categorie) },
                        onClick = {
                            categorieSelectionnee = categorie
                            menuOuvert = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Bouton créer championnat
        Button(
            onClick = {
                if (nomChamp.isNotBlank()) {
                    // On passe le nom et la catégorie sélectionnée
                    viewModel.creerChampionnat(nomChamp, categorieSelectionnee)

                    // Navigation vers la création d'équipes
                    navController.navigate("creer_equipe/$nomChamp")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Créer championnat")
        }

    }
}
