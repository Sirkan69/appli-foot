package com.example.monclassementfoot.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun PageHeader(
    navController: NavHostController,
    title: String,
    scrollable: Boolean = true, // permet de choisir si le contenu doit défiler
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (scrollable) Modifier.verticalScroll(rememberScrollState())
                else Modifier
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // === Header ===
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            BackButton(navController = navController)
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            HomeButton(navController = navController)
        }

        // === Séparateur ===
        Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)

        // === Contenu de la page ===
        content()
    }
}
