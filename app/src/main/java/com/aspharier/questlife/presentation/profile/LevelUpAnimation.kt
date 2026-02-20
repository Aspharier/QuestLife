package com.aspharier.questlife.presentation.profile

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import kotlinx.coroutines.delay

@Composable
fun LevelUpAnimation(level: Int) {

    var visible by remember { mutableStateOf(true) }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1.3f else 1f,
        animationSpec = tween(600)
    )

    LaunchedEffect(Unit) {
        delay(1500)
        visible = false
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "LEVEL UP! $level",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.scale(scale),
            color = MaterialTheme.colorScheme.primary
        )
    }
}
