package com.aspharier.questlife.presentation.avatar

import androidx.compose.animation.core.*
import androidx.compose.runtime.*

@Composable
fun rememberIdleAnimation(): Float {

    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "idleBreathing"
    )

    return scale
}
