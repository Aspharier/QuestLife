package com.aspharier.questlife.presentation.habits


import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StreakFlame(
    streak: Int
) {

    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flamePulse"
    )

    val intensityColor = when {
        streak >= 30 -> Color(0xFFFF6F00)
        streak >= 7 -> Color(0xFFFF9800)
        else -> Color(0xFFFF5722)
    }

    Box(
        modifier = Modifier
            .size(28.dp)
            .scale(scale)
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Streak",
            tint = intensityColor
        )
    }
}
