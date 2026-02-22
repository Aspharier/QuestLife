package com.aspharier.questlife.presentation.avatar

import androidx.compose.animation.core.*
import androidx.compose.runtime.*

@Composable
fun rememberVictoryAnimation(
    isLevelUp: Boolean
): Float {

    val scale = remember { Animatable(1f) }

    LaunchedEffect(isLevelUp) {
        if (isLevelUp) {
            scale.animateTo(
                1.3f,
                animationSpec = tween(300)
            )
            scale.animateTo(
                1f,
                animationSpec = tween(500)
            )
        }
    }

    return scale.value
}