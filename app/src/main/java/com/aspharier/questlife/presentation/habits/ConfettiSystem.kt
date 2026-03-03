package com.aspharier.questlife.presentation.habits

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private data class Particle(
        val angle: Float,
        val velocity: Float,
        val color: Color,
        val size: Float,
        val rotSpeed: Float
)

@Composable
fun ConfettiSystem(
        modifier: Modifier = Modifier,
        particleCount: Int = 25,
) {
    val particles = remember {
        List(particleCount) {
            Particle(
                    angle = Random.nextFloat() * 360f,
                    velocity = Random.nextFloat() * 400f + 200f,
                    color =
                            listOf(
                                            Color(0xFFFFD700),
                                            Color(0xFFEC4899),
                                            Color(0xFF8B5CF6),
                                            Color(0xFF10B981),
                                            Color(0xFF3B82F6)
                                    )
                                    .random(),
                    size = Random.nextFloat() * 8f + 6f, // 6 to 14
                    rotSpeed = Random.nextFloat() * 4f - 2f
            )
        }
    }

    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animationProgress.snapTo(0f)
        animationProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        )
    }

    Canvas(modifier = modifier) {
        particles.forEach { particle ->
            val radians = Math.toRadians(particle.angle.toDouble())

            val progress = animationProgress.value

            val x = center.x + cos(radians).toFloat() * particle.velocity * progress

            val y =
                    center.y + sin(radians).toFloat() * particle.velocity * progress -
                            (300 * progress * progress * 1.5f) // gravity

            val rotationCurrent = particle.rotSpeed * progress * 360f

            withTransform({
                translate(left = x, top = y)
                rotate(degrees = rotationCurrent)
            }) {
                drawRect(
                        color = particle.color.copy(alpha = 1f - progress),
                        size =
                                androidx.compose.ui.geometry.Size(
                                        particle.size.dp.toPx(),
                                        (particle.size * 0.6f).dp.toPx()
                                )
                )
            }
        }
    }
}
