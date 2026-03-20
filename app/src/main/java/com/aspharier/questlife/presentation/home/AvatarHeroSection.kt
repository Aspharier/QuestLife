package com.aspharier.questlife.presentation.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspharier.questlife.core.ui.animations.bounceClickable
import com.aspharier.questlife.core.ui.components.GamePanel
import com.aspharier.questlife.core.ui.components.StatBar
import com.aspharier.questlife.core.ui.theme.LocalGameColors
import com.aspharier.questlife.presentation.avatar.AvatarRenderer
import com.aspharier.questlife.presentation.avatar.AvatarState
import com.aspharier.questlife.presentation.companion.CompanionType
import kotlin.math.cos
import kotlin.math.sin

private var hasPlayedAvatarAnimation = false

@Composable
fun AvatarHeroSection(
    level: Int,
    totalXp: Int,
    progress: Float,
    persona: com.aspharier.questlife.domain.model.Persona,
    companionType: CompanionType = CompanionType.WOLF,
    onAvatarClick: () -> Unit
) {
    var targetLevel by remember { mutableIntStateOf(if (hasPlayedAvatarAnimation) level else 1) }
    var targetXp by remember { mutableIntStateOf(if (hasPlayedAvatarAnimation) totalXp else 0) }
    var targetProgress by remember { mutableFloatStateOf(if (hasPlayedAvatarAnimation) progress else 0f) }

    LaunchedEffect(level, totalXp, progress) {
        if (!hasPlayedAvatarAnimation) {
            delay(600) // slight delay on open
            targetLevel = level
            targetXp = totalXp
            targetProgress = progress
            hasPlayedAvatarAnimation = true
        } else {
            targetLevel = level
            targetXp = totalXp
            targetProgress = progress
        }
    }

    val displayedLevel by animateIntAsState(
        targetValue = targetLevel,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "levelAnim"
    )

    val displayedXp by animateIntAsState(
        targetValue = targetXp,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "xpAnim"
    )

    val displayedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "progressAnim"
    )

    val gameColors = LocalGameColors.current
    val primaryColor = MaterialTheme.colorScheme.primary
    val companionColor = Color(companionType.primaryColor)

    GamePanel(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .bounceClickable { onAvatarClick() },
        borderColor = primaryColor.copy(alpha = 0.3f),
        glowColor = primaryColor.copy(alpha = 0.08f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f),
                            gameColors.panelBackground.copy(alpha = 0.02f)
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Animated values ---
            val infiniteTransition = rememberInfiniteTransition(label = "heroAnim")

            val floatOffset by infiniteTransition.animateFloat(
                initialValue = -4f, targetValue = 4f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = EaseInOutSine),
                    repeatMode = RepeatMode.Reverse
                ), label = "float"
            )

            val ringGlow by infiniteTransition.animateFloat(
                initialValue = 0.3f, targetValue = 0.7f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1500, easing = EaseInOutSine),
                    repeatMode = RepeatMode.Reverse
                ), label = "ringGlow"
            )

            val runeRotation by infiniteTransition.animateFloat(
                initialValue = 0f, targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(12000, easing = LinearEasing)
                ), label = "runeRotation"
            )

            val companionPulse by infiniteTransition.animateFloat(
                initialValue = 0.6f, targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1200, easing = EaseInOutSine),
                    repeatMode = RepeatMode.Reverse
                ), label = "companionPulse"
            )

            // --- Avatar with emblem ring and companion badge ---
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .graphicsLayer { translationY = floatOffset.dp.toPx() },
                contentAlignment = Alignment.Center
            ) {
                // Orbiting rune dots (class emblem ring)
                Canvas(modifier = Modifier.size(148.dp)) {
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val radius = size.width / 2f - 4.dp.toPx()
                    val dotCount = 8
                    for (i in 0 until dotCount) {
                        val angle = Math.toRadians(
                            (runeRotation + (360.0 / dotCount) * i)
                        )
                        val dotX = center.x + radius * cos(angle).toFloat()
                        val dotY = center.y + radius * sin(angle).toFloat()
                        val dotAlpha = 0.3f + (ringGlow * 0.5f)
                        drawCircle(
                            color = primaryColor.copy(alpha = dotAlpha),
                            radius = 3.dp.toPx(),
                            center = Offset(dotX, dotY)
                        )
                    }
                }

                // Outer glow ring
                Box(
                    modifier = Modifier
                        .size(134.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.5.dp,
                            brush = Brush.sweepGradient(
                                colors = listOf(
                                    primaryColor.copy(alpha = ringGlow),
                                    primaryColor.copy(alpha = 0.05f),
                                    companionColor.copy(alpha = ringGlow * 0.7f),
                                    primaryColor.copy(alpha = 0.05f),
                                    primaryColor.copy(alpha = ringGlow)
                                )
                            ),
                            shape = CircleShape
                        )
                )

                // Inner ring
                Box(
                    modifier = Modifier
                        .size(124.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = primaryColor.copy(alpha = 0.15f),
                            shape = CircleShape
                        )
                )

                // Avatar circle
                Box(
                    modifier = Modifier
                        .size(108.dp)
                        .shadow(
                            14.dp, CircleShape,
                            spotColor = primaryColor.copy(alpha = 0.5f)
                        )
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    primaryColor.copy(alpha = 0.3f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AvatarRenderer(
                        state = AvatarState(avatarClass = persona.avatarClass),
                        modifier = Modifier.size(72.dp)
                    )
                }

                // Companion badge (bottom-right)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = (-8).dp, y = (-4).dp)
                        .size(40.dp)
                        .shadow(
                            8.dp, CircleShape,
                            spotColor = companionColor.copy(alpha = 0.5f)
                        )
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    companionColor.copy(alpha = 0.25f),
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                                )
                            )
                        )
                        .border(
                            width = 1.5.dp,
                            color = companionColor.copy(alpha = companionPulse * 0.6f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = companionType.emoji,
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            // --- Title banner (ribbon-style) ---
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                primaryColor.copy(alpha = 0.14f),
                                primaryColor.copy(alpha = 0.18f),
                                primaryColor.copy(alpha = 0.14f),
                                Color.Transparent
                            )
                        )
                    )
                    .border(
                        width = 1.dp,
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                primaryColor.copy(alpha = 0.3f),
                                primaryColor.copy(alpha = 0.4f),
                                primaryColor.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 24.dp, vertical = 5.dp)
            ) {
                Text(
                    text = persona.title.uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )
            }

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Level $displayedLevel",
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(Modifier.height(14.dp))

            // XP Bar
            Column(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StatBar(
                    progress = displayedProgress,
                    barColor = gameColors.xpBar,
                    barColorEnd = gameColors.xpBarSecondary,
                    height = 14.dp
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "$displayedXp XP",
                    style = MaterialTheme.typography.labelLarge,
                    color = gameColors.xpBar,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
