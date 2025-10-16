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
fun VignetteCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    title: String = "",
    subtitleRight: String? = null,
    bandeauColorStart: Color = Color(0xFFA4C2F4),
    bandeauColorEnd: Color = Color(0xFF7CA6F1),
    content: @Composable ColumnScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Animation de l’élévation
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 16.dp else 8.dp,
        label = "elevationAnimation"
    )

    // Animation du bandeau
    val bandeauGradient = Brush.horizontalGradient(
        colors = listOf(
            animateColorAsState(
                if (isPressed) bandeauColorStart.copy(alpha = 0.9f)
                else bandeauColorStart,
                label = "startColorAnim"
            ).value,
            animateColorAsState(
                if (isPressed) bandeauColorEnd.copy(alpha = 0.9f)
                else bandeauColorEnd,
                label = "endColorAnim"
            ).value
        )
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
            .clickable(interactionSource = interactionSource, indication = null) { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // Bandeau supérieur avec effet animé
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(bandeauGradient)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                if (!subtitleRight.isNullOrEmpty()) {
                    Text(
                        text = subtitleRight,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }
            }

            // Contenu personnalisé
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                content()
            }
        }
    }
}
