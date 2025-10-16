package com.example.monclassementfoot.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VignetteEquipeCard(
    nomEquipe: String,
    couleurStart: Color,
    couleurEnd: Color,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val elevation by animateDpAsState(
        targetValue = if (isPressed) 16.dp else 8.dp,
        label = "elevationEquipe"
    )

    val gradient = Brush.horizontalGradient(
        colors = listOf(
            animateColorAsState(
                targetValue = if (isPressed) couleurStart.copy(alpha = 0.9f) else couleurStart,
                label = "startColor"
            ).value,
            animateColorAsState(
                targetValue = if (isPressed) couleurEnd.copy(alpha = 0.9f) else couleurEnd,
                label = "endColor"
            ).value
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(20.dp))
            .clickable(interactionSource = interactionSource, indication = null) { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // Bandeau dégradé avec nom de l'équipe
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(gradient)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = nomEquipe,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            // Contenu supplémentaire sous le bandeau
            Column(
                modifier = Modifier.padding(16.dp),
                content = content
            )
        }
    }
}
