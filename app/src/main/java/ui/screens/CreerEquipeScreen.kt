package com.example.monclassementfoot.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.monclassementfoot.viewmodels.ChampionshipViewModel
import com.example.monclassementfoot.viewmodels.Equipe
import com.example.monclassementfoot.ui.components.HomeButton
import com.example.monclassementfoot.ui.components.BackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreerEquipeScreen(
    navController: NavHostController,
    viewModel: ChampionshipViewModel,
    nomChampionnat: String,
    equipeExistante: Equipe? = null
) {
    // Nom de l'équipe
    var nomEquipe by remember { mutableStateOf(equipeExistante?.nom ?: "") }

    // Coachs et joueurs
    val listeCoach = remember { mutableStateListOf<Pair<String, String>>().apply { equipeExistante?.coachs?.let { addAll(it) } } }
    val listeJoueurs = remember { mutableStateListOf<Triple<String, List<String>, String>>().apply { equipeExistante?.joueurs?.let { addAll(it) } } }

    // Couleurs
    var couleurPrincipale by remember { mutableStateOf(equipeExistante?.couleurPrincipale ?: Color(0xFF1E88E5)) }
    var couleurSecondaire by remember { mutableStateOf(equipeExistante?.couleurSecondaire ?: Color(0xFFD32F2F)) }

    // Etats des menus de sélection de couleur
    var showColorGridPrincipale by remember { mutableStateOf(false) }
    var showColorGridSecondaire by remember { mutableStateOf(false) }

    var showCoachDialog by remember { mutableStateOf(false) }
    var showPlayerDialog by remember { mutableStateOf(false) }

    var coachEnEdition by remember { mutableStateOf<Pair<String, String>?>(null) }
    var joueurEnEdition by remember { mutableStateOf<Triple<String, List<String>, String>?>(null) }

    val scrollState = rememberScrollState()

    val couleursDisponibles = listOf(
        Color.Red, Color.Green, Color.Blue, Color.Yellow,
        Color.Cyan, Color.Magenta, Color.Black, Color.Gray, Color.White,
        Color(0xFFFFA500), Color(0xFF800080), Color(0xFF00FFFF),
        Color(0xFF008000), Color(0xFF808000), Color(0xFF800000),
        Color(0xFF4682B4), Color(0xFFFFC0CB)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // === Ligne du haut : Bouton Home + Titre + Bouton Liste équipes ===
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // permet d'espacer les éléments sur toute la largeur
        ) {
            // --- Bouton Home ---
            HomeButton(navController = navController)

            // --- Titre ---
            Text(
                text = "Créer ton équipe",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            // --- Backbutton ---
            BackButton(navController = navController) // simple et réutilisable

        }

        // --- Séparateur ---
        Divider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )

        // === Nom de l'équipe ===
        OutlinedTextField(
            value = nomEquipe,
            onValueChange = { nomEquipe = it },
            label = { Text("Nom de l'équipe") },
            modifier = Modifier.fillMaxWidth()
        )

        // === Sélection couleurs ===
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { showColorGridPrincipale = !showColorGridPrincipale }) {
                Text("Couleur principale")
            }
            Spacer(Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(couleurPrincipale, RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            )
        }

        if (showColorGridPrincipale) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.height(200.dp)
            ) {
                items(couleursDisponibles) { couleur ->
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(4.dp)
                            .background(couleur, RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                            .clickable {
                                couleurPrincipale = couleur
                                showColorGridPrincipale = false
                            }
                    )
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { showColorGridSecondaire = !showColorGridSecondaire }) {
                Text("Couleur secondaire")
            }
            Spacer(Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(couleurSecondaire, RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            )
        }

        if (showColorGridSecondaire) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.height(200.dp)
            ) {
                items(couleursDisponibles) { couleur ->
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(4.dp)
                            .background(couleur, RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                            .clickable {
                                couleurSecondaire = couleur
                                showColorGridSecondaire = false
                            }
                    )
                }
            }
        }

        // === Boutons pour ajouter Coach et Joueur ===
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { showCoachDialog = true }) { Text("Ajouter Coach") }
            Button(onClick = { showPlayerDialog = true }) { Text("Ajouter Joueur") }
        }

        // === Dialog Coach ===
        if (showCoachDialog || coachEnEdition != null) {
            Dialog(onDismissRequest = {
                showCoachDialog = false
                coachEnEdition = null
            }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    var nomCoach by remember { mutableStateOf(coachEnEdition?.first ?: "") }
                    var roleCoach by remember {
                        mutableStateOf(
                            coachEnEdition?.second ?: "Entraîneur principal"
                        )
                    }

                    val roles = listOf(
                        "Entraîneur principal",
                        "Entraîneur adjoint",
                        "Entraîneur des gardiens"
                    )

                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            if (coachEnEdition == null) "Ajouter un coach" else "Modifier un coach",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        OutlinedTextField(
                            value = nomCoach,
                            onValueChange = { nomCoach = it },
                            label = { Text("Nom du coach") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Sélection simple via bouton (au lieu de DropdownMenuBox)
                        roles.forEach { r ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { roleCoach = r }
                                    .padding(4.dp)
                            ) {
                                RadioButton(selected = roleCoach == r, onClick = { roleCoach = r })
                                Text(r, modifier = Modifier.padding(start = 8.dp))
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (coachEnEdition != null) {
                                TextButton(onClick = {
                                    listeCoach.remove(coachEnEdition)
                                    coachEnEdition = null
                                }) { Text("Supprimer", color = Color.Red) }
                            }
                            Button(onClick = {
                                if (nomCoach.isNotBlank()) {
                                    if (coachEnEdition == null) {
                                        listeCoach.add(Pair(nomCoach, roleCoach))
                                    } else {
                                        val index = listeCoach.indexOf(coachEnEdition!!)
                                        listeCoach[index] = Pair(nomCoach, roleCoach)
                                        coachEnEdition = null
                                    }
                                    showCoachDialog = false
                                }
                            }) { Text("Valider") }
                        }
                    }
                }
            }
        }

        // === Dialog Joueur ===
        if (showPlayerDialog || joueurEnEdition != null) {
            Dialog(onDismissRequest = {
                showPlayerDialog = false
                joueurEnEdition = null
            }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    var nomJoueur by remember { mutableStateOf(joueurEnEdition?.first ?: "") }
                    var numMaillot by remember { mutableStateOf(joueurEnEdition?.third ?: "") }
                    val postesDisponibles = listOf("Gardien", "Défenseur", "Milieu", "Attaquant")
                    val postesSelectionnes = remember {
                        mutableStateListOf<String>().apply {
                            joueurEnEdition?.second?.let { addAll(it) }
                        }
                    }

                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            if (joueurEnEdition == null) "Ajouter un joueur" else "Modifier un joueur",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        OutlinedTextField(
                            value = nomJoueur,
                            onValueChange = { nomJoueur = it },
                            label = { Text("Nom du joueur") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = numMaillot,
                            onValueChange = { newValue ->
                                if (newValue.all { it.isDigit() }) numMaillot = newValue
                            },
                            label = { Text("Numéro de maillot (facultatif)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text("Postes", fontWeight = FontWeight.Bold)
                        postesDisponibles.forEach { poste ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (postesSelectionnes.contains(poste)) postesSelectionnes.remove(
                                            poste
                                        )
                                        else postesSelectionnes.add(poste)
                                    }
                                    .padding(4.dp)
                            ) {
                                Checkbox(
                                    checked = postesSelectionnes.contains(poste),
                                    onCheckedChange = {
                                        if (it) postesSelectionnes.add(poste) else postesSelectionnes.remove(
                                            poste
                                        )
                                    }
                                )
                                Text(poste)
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (joueurEnEdition != null) {
                                TextButton(onClick = {
                                    listeJoueurs.remove(joueurEnEdition)
                                    joueurEnEdition = null
                                }) { Text("Supprimer", color = Color.Red) }
                            }
                            Button(onClick = {
                                if (nomJoueur.isNotBlank() && postesSelectionnes.isNotEmpty()) {
                                    val newPlayer =
                                        Triple(nomJoueur, postesSelectionnes.toList(), numMaillot)
                                    if (joueurEnEdition == null) {
                                        listeJoueurs.add(newPlayer)
                                    } else {
                                        val index = listeJoueurs.indexOf(joueurEnEdition!!)
                                        listeJoueurs[index] = newPlayer
                                        joueurEnEdition = null
                                    }
                                    showPlayerDialog = false
                                }
                            }) { Text("Valider") }
                        }
                    }
                }
            }
        }

        // === Bloc récapitulatif ===
        Spacer(Modifier.height(12.dp))
        Text(
            "Récapitulatif",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column {
                // Bandeau dégradé avec nom de l'équipe
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(couleurPrincipale, couleurSecondaire)
                            ),
                            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = if (nomEquipe.isNotBlank()) nomEquipe else "Nom de l'équipe",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = if (couleurPrincipale.luminance() < 0.5f) Color.White else Color.Black,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                // Contenu de la carte
                Column(modifier = Modifier.padding(16.dp)) {
                    // Coachs
                    if (listeCoach.isNotEmpty()) {
                        Text("🧑‍🏫 Coachs", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(Modifier.height(8.dp))
                        listeCoach.forEach { coach ->
                            Text("${coach.first} (${coach.second})")
                        }
                        Spacer(Modifier.height(12.dp))
                    }

                    // Joueurs
                    if (listeJoueurs.isNotEmpty()) {
                        Text("⚽ Joueurs", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(Modifier.height(8.dp))
                        listeJoueurs.forEach { joueur ->
                            Text("${joueur.first} - ${joueur.second.joinToString("/")} #${joueur.third}")
                        }
                    }

                    // Bouton pour valider la création de l'équipe
                    Button(
                        onClick = {
                            if (nomEquipe.isNotBlank()) {
                                viewModel.ajouterEquipe(
                                    nomChampionnat,
                                    Equipe(
                                        nom = nomEquipe,
                                        coachs = listeCoach.toList(),
                                        joueurs = listeJoueurs.toList(),
                                        couleurPrincipale = couleurPrincipale,
                                        couleurSecondaire = couleurSecondaire
                                    )
                                )
                                navController.navigate("liste_equipes/$nomChampionnat")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text("Valider la création de l'équipe")
                    }
                }
            }
        }
    }
}