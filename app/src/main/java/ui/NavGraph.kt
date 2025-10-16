package com.example.monclassementfoot.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.monclassementfoot.ui.screens.CreateChampionshipScreen
import com.example.monclassementfoot.ui.screens.CreerEquipeScreen
import com.example.monclassementfoot.ui.screens.ListeEquipesScreen
import com.example.monclassementfoot.ui.screens.AccueilScreen
import com.example.monclassementfoot.viewmodels.ChampionshipViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: ChampionshipViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "accueil" // <- Nouvelle start destination
    ) {

        // ------------------------------------------------------------
        // Écran d'accueil : liste des championnats existants
        // ------------------------------------------------------------
        composable("accueil") {
            AccueilScreen(viewModel = viewModel, navController = navController)
        }

        // ------------------------------------------------------------
        // Création d’un championnat
        // Navigué depuis le FAB de AccueilScreen
        // ------------------------------------------------------------
        composable("create_championship") {
            CreateChampionshipScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        // ------------------------------------------------------------
        // Création d’une équipe pour un championnat
        // Le nom du championnat est passé en argument
        // ------------------------------------------------------------
        composable("creer_equipe/{nomChampionnat}") { backStackEntry ->
            val nomChampionnat = backStackEntry.arguments?.getString("nomChampionnat") ?: ""
            CreerEquipeScreen(
                navController = navController,
                viewModel = viewModel,
                nomChampionnat = nomChampionnat
            )
        }

        // ------------------------------------------------------------
        // Liste des équipes d’un championnat
        // Le nom du championnat est passé en argument
        // ------------------------------------------------------------
        composable("liste_equipes/{nomChampionnat}") { backStackEntry ->
            val nomChampionnat = backStackEntry.arguments?.getString("nomChampionnat") ?: ""
            ListeEquipesScreen(
                navController = navController,
                viewModel = viewModel,
                nomChampionnat = nomChampionnat
            )
        }
        composable(
            route = "creer_equipe/{nomChampionnat}/{nomEquipe}",
            arguments = listOf(
                navArgument("nomChampionnat") { type = NavType.StringType },
                navArgument("nomEquipe") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val nomChampionnat = backStackEntry.arguments?.getString("nomChampionnat") ?: ""
            val nomEquipe = backStackEntry.arguments?.getString("nomEquipe") ?: ""

            val equipeExistante = if (nomEquipe.isNotBlank()) {
                viewModel.getEquipes(nomChampionnat).find { it.nom == nomEquipe }
            } else null

            CreerEquipeScreen(
                navController = navController,
                viewModel = viewModel,
                nomChampionnat = nomChampionnat,
                equipeExistante = equipeExistante
            )
        }
    }
}

