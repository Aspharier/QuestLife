package com.aspharier.questlife.presentation.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspharier.questlife.core.ui.animations.bounceClickable
import com.aspharier.questlife.core.ui.components.GamePanel
import com.aspharier.questlife.core.ui.components.StatBar
import com.aspharier.questlife.core.ui.theme.LocalGameColors

@Composable
fun AvatarHeroSection(
    level: Int,
    totalXp: Int,
    progress: Float,
    onAvatarClick: () -> Unit
) {
        val gameColors = LocalGameColors.current

        GamePanel(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .bounceClickable { onAvatarClick() },
                borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                glowColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
        ) {
                Column(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .background(
                                                Brush.verticalGradient(
                                                        colors =
                                                                listOf(
                                                                        MaterialTheme.colorScheme
                                                                                .primaryContainer
                                                                                .copy(
                                                                                        alpha =
                                                                                                0.15f
                                                                                ),
                                                                        gameColors.panelBackground
                                                                                .copy(alpha = 0.02f)
                                                                )
                                                )
                                        )
                                        .padding(horizontal = 16.dp, vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                        // Floating animation for Avatar
                        val infiniteTransition = rememberInfiniteTransition(label = "avatarFloat")
                        val floatOffset by
                                infiniteTransition.animateFloat(
                                        initialValue = -4f,
                                        targetValue = 4f,
                                        animationSpec =
                                                infiniteRepeatable(
                                                        animation =
                                                                tween(2000, easing = EaseInOutSine),
                                                        repeatMode = RepeatMode.Reverse
                                                ),
                                        label = "float"
                                )

                        // Glowing ring behind avatar
                        val ringGlow by
                                infiniteTransition.animateFloat(
                                        initialValue = 0.3f,
                                        targetValue = 0.7f,
                                        animationSpec =
                                                infiniteRepeatable(
                                                        animation =
                                                                tween(1500, easing = EaseInOutSine),
                                                        repeatMode = RepeatMode.Reverse
                                                ),
                                        label = "ringGlow"
                                )

                        Box(
                                modifier =
                                        Modifier.size(120.dp).graphicsLayer {
                                                translationY = floatOffset.dp.toPx()
                                        },
                                contentAlignment = Alignment.Center
                        ) {
                                // Outer glow ring
                                Box(
                                        modifier =
                                                Modifier.size(120.dp)
                                                        .clip(CircleShape)
                                                        .border(
                                                                width = 2.dp,
                                                                brush =
                                                                        Brush.radialGradient(
                                                                                colors =
                                                                                        listOf(
                                                                                                MaterialTheme
                                                                                                        .colorScheme
                                                                                                        .primary
                                                                                                        .copy(
                                                                                                                alpha =
                                                                                                                        ringGlow
                                                                                                        ),
                                                                                                MaterialTheme
                                                                                                        .colorScheme
                                                                                                        .primary
                                                                                                        .copy(
                                                                                                                alpha =
                                                                                                                        0.05f
                                                                                                        )
                                                                                        )
                                                                        ),
                                                                shape = CircleShape
                                                        )
                                )

                                // Avatar circle
                                Box(
                                        modifier =
                                                Modifier.size(100.dp)
                                                        .shadow(
                                                                12.dp,
                                                                CircleShape,
                                                                spotColor =
                                                                        MaterialTheme.colorScheme
                                                                                .primary.copy(
                                                                                alpha = 0.5f
                                                                        )
                                                        )
                                                        .clip(CircleShape)
                                                        .background(
                                                                Brush.radialGradient(
                                                                        colors =
                                                                                listOf(
                                                                                        MaterialTheme
                                                                                                .colorScheme
                                                                                                .primaryContainer,
                                                                                        MaterialTheme
                                                                                                .colorScheme
                                                                                                .primary
                                                                                                .copy(
                                                                                                        alpha =
                                                                                                                0.3f
                                                                                                )
                                                                                )
                                                                )
                                                        ),
                                        contentAlignment = Alignment.Center
                                ) { Text("⚔️", fontSize = 48.sp) }
                        }

                        Spacer(Modifier.height(12.dp))

                        // Class name badge
                        Box(
                                modifier =
                                        Modifier.clip(RoundedCornerShape(8.dp))
                                                .background(
                                                        MaterialTheme.colorScheme.primary.copy(
                                                                alpha = 0.12f
                                                        )
                                                )
                                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                                Text(
                                        text = "WARRIOR",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 3.sp
                                )
                        }

                        Spacer(Modifier.height(6.dp))

                        Text(
                                text = "Level $level",
                                style = MaterialTheme.typography.headlineLarge,
                        )

                        Spacer(Modifier.height(14.dp))

                        // XP Bar with game styling
                        Column(
                                modifier = Modifier.fillMaxWidth(0.8f),
                                horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                                StatBar(
                                        progress = progress,
                                        barColor = gameColors.xpBar,
                                        barColorEnd = gameColors.xpBarSecondary,
                                        height = 14.dp
                                )

                                Spacer(Modifier.height(6.dp))

                                Text(
                                        text = "$totalXp XP",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = gameColors.xpBar,
                                        fontWeight = FontWeight.SemiBold
                                )
                        }
                }
        }
}
