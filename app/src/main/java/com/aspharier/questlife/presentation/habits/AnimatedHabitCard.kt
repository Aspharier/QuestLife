package com.aspharier.questlife.presentation.habits

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspharier.questlife.presentation.quests.QuestCard
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedHabitCard(
    habitName: String,
    meta: String,
    isCompleted: Boolean,
    xpGained: Int?,
    onAnimationEnd: () -> Unit,
    onClick: () -> Unit,
    onLongPress: () -> Unit
) {
    var animate by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (animate) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = "scale"
    )

    val glowAlpha by animateFloatAsState(
        targetValue = if (animate) 0.3f else 0f,
        animationSpec = tween(600),
        label = "glow"
    )

    LaunchedEffect(isCompleted) {
        if (isCompleted) {
            animate = true
            delay(800)
            animate = false
            delay(200)
            onAnimationEnd()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .scale(scale)
    ) {

        QuestCard(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongPress
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = habitName,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = meta,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        // Glow Overlay
        if (glowAlpha > 0f) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = glowAlpha),
                                MaterialTheme.colorScheme.primary.copy(alpha = 0f)
                            )
                        )
                    )
            )
        }

        // XP popup
        xpGained?.let { xp ->
            XPPopup(xp = xp)
        }
    }
}

@Composable
fun XPPopup(xp: Int) {

    var visible by remember { mutableStateOf(true) }

    val offsetY by animateFloatAsState(
        targetValue = if (visible) -80f else 0f,
        animationSpec = tween(800),
        label = "xpOffset"
    )

    val alpha by animateFloatAsState(
        targetValue = if (visible) 0f else 1f,
        animationSpec = tween(800),
        label = "xpAlpha"
    )

    LaunchedEffect(Unit) {
        visible = true
        delay(800)
        visible = false
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = offsetY.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = "+$xp XP",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
