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
import com.aspharier.questlife.presentation.home.getActiveWarrior
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

private var hasPlayedAvatarAnimation = false

@Composable
fun AvatarHeroSection(
    level: Int,
    totalXp: Int,
    progress: Float,
    persona: com.aspharier.questlife.domain.model.Persona,
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
    val activeWarrior = remember(persona.selectedWarrior, level) {
        val selected = warriorTiers.find { it.name == persona.selectedWarrior }
        if (selected != null && selected.levelRequired <= level) selected
        else getActiveWarrior(level)
    }

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
                                    activeWarrior.glowColor.copy(alpha = ringGlow * 0.7f),
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
                    Image(
                        painter = painterResource(id = activeWarrior.drawableRes),
                        contentDescription = activeWarrior.name,
                        modifier = Modifier.size(72.dp)
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

            // Warrior name + rarity
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "⚔️",
                    fontSize = 14.sp
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = activeWarrior.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = activeWarrior.glowColor,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(activeWarrior.glowColor.copy(alpha = 0.15f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = activeWarrior.rarityLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = activeWarrior.glowColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.sp
                    )
                }
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
