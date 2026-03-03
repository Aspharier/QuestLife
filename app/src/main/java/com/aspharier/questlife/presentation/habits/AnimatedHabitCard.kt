package com.aspharier.questlife.presentation.habits

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspharier.questlife.core.ui.theme.QuestCard
import com.aspharier.questlife.core.ui.theme.streakFlame
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedHabitCard(
        habitName: String,
        meta: String,
        isCompleted: Boolean,
        isCompletedToday: Boolean,
        streak: Int,
        xpGained: Int?,
        onAnimationEnd: () -> Unit,
        onClick: () -> Unit,
        onLongPress: () -> Unit
) {
        var animate by remember { mutableStateOf(false) }

        val scale by
                animateFloatAsState(
                        targetValue = if (animate) 1.05f else 1f,
                        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                        label = "scale"
                )

        val glowAlpha by
                animateFloatAsState(
                        targetValue = if (animate) 0.35f else 0f,
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

        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).scale(scale)) {
                QuestCard(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .combinedClickable(
                                                onClick = onClick,
                                                onLongClick = onLongPress
                                        )
                ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                ) {
                                        // Completed-today checkmark badge
                                        if (isCompletedToday) {
                                                Box(
                                                        modifier =
                                                                Modifier.clip(
                                                                                RoundedCornerShape(
                                                                                        6.dp
                                                                                )
                                                                        )
                                                                        .background(
                                                                                Color(0xFF22C55E)
                                                                                        .copy(
                                                                                                alpha =
                                                                                                        0.15f
                                                                                        )
                                                                        )
                                                                        .padding(
                                                                                horizontal = 8.dp,
                                                                                vertical = 3.dp
                                                                        )
                                                ) {
                                                        Text(
                                                                text = "✓ Done",
                                                                fontSize = 11.sp,
                                                                color = Color(0xFF22C55E),
                                                                fontWeight = FontWeight.Bold
                                                        )
                                                }
                                        }
                                }

                                Spacer(Modifier.height(2.dp))

                                Text(
                                        text = habitName,
                                        style = MaterialTheme.typography.titleLarge,
                                        color =
                                                if (isCompletedToday)
                                                        MaterialTheme.colorScheme.onSurface.copy(
                                                                alpha = 0.5f
                                                        )
                                                else MaterialTheme.colorScheme.onSurface
                                )

                                Spacer(Modifier.height(6.dp))

                                Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                ) {
                                        Text(
                                                text = meta,
                                                style = MaterialTheme.typography.labelLarge,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        // Real streak from DB
                                        if (streak > 0) {
                                                Row(
                                                        verticalAlignment =
                                                                Alignment.CenterVertically
                                                ) {
                                                        StreakFlame(streak = streak)
                                                        Spacer(Modifier.width(4.dp))
                                                        Text(
                                                                text = "$streak",
                                                                style =
                                                                        MaterialTheme.typography
                                                                                .labelLarge,
                                                                color =
                                                                        MaterialTheme.colorScheme
                                                                                .onSurfaceVariant
                                                        )
                                                }
                                        }
                                }
                        }
                }

                // Streak Milestone Overlay
                if (streak > 0 && streak % 5 == 0 && isCompleted) {
                        Box(
                                modifier =
                                        Modifier.matchParentSize()
                                                .clip(RoundedCornerShape(16.dp))
                                                .background(Color.Black.copy(alpha = 0.5f)),
                                contentAlignment = Alignment.Center
                        ) {
                                Text(
                                        text = "🔥 $streak Days!",
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = streakFlame,
                                        modifier = Modifier.scale(scale),
                                        fontWeight = FontWeight.ExtraBold
                                )
                        }
                }

                // Glow Overlay on completion animation
                if (glowAlpha > 0f) {
                        Box(
                                modifier =
                                        Modifier.matchParentSize()
                                                .clip(RoundedCornerShape(16.dp))
                                                .background(
                                                        Brush.verticalGradient(
                                                                colors =
                                                                        listOf(
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .primary
                                                                                        .copy(
                                                                                                alpha =
                                                                                                        glowAlpha
                                                                                        ),
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .primary
                                                                                        .copy(
                                                                                                alpha =
                                                                                                        0f
                                                                                        )
                                                                        )
                                                        )
                                                )
                        )
                }

                // XP popup
                xpGained?.let { xp -> XPPopup(xp = xp) }

                if (isCompleted) {
                        ConfettiSystem(modifier = Modifier.fillMaxSize())
                }
        }
}

@Composable
fun XPPopup(xp: Int) {
        var isAnimating by remember { mutableStateOf(false) }

        val offsetY by
                animateFloatAsState(
                        targetValue = if (isAnimating) -80f else 0f,
                        animationSpec = tween(800),
                        label = "xpOffset"
                )
        val alpha by
                animateFloatAsState(
                        targetValue = if (isAnimating) 0f else 1f,
                        animationSpec = tween(800),
                        label = "xpAlpha"
                )

        LaunchedEffect(Unit) {
                isAnimating = true
                delay(800)
                isAnimating = false
        }

        Box(
                modifier = Modifier.fillMaxWidth().offset(y = offsetY.dp).alpha(1f - alpha),
                contentAlignment = Alignment.TopCenter
        ) {
                Text(
                        text = "+$xp XP ⚡",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD700)
                )
        }
}
