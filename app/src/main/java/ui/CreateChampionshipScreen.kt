package com.example.monpremiereapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateChampionshipScreen(onSaved: (String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var ageCategory by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Créer un championnat", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nom du championnat") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = ageCategory,
            onValueChange = { ageCategory = it },
            label = { Text("Catégorie d'âge (ex: U14, Senior)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        if (showError) {
            Text("Veuillez remplir tous les champs", color = MaterialTheme.colors.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = {
                if (name.isBlank() || ageCategory.isBlank()) {
                    showError = true
                } else {
                    showError = false
                    onSaved(name.trim(), ageCategory.trim())
                }
            }) {
                Text("Enregistrer")
            }
        }
    }
}
