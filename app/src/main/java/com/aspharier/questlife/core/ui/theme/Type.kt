package com.aspharier.questlife.core.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aspharier.questlife.core.ui.animations.GlassCard
import com.aspharier.questlife.core.ui.animations.bounceClickable
import com.questlife.app.R

// Custom Typography

val Inter =
        FontFamily(
            Font(R.font.inter_regular, FontWeight.Normal),
            Font(R.font.inter_medium, FontWeight.Medium),
            Font(R.font.inter_bold, FontWeight.Bold)
        )

val QuestLifeTypography =
        Typography(
                displayLarge =
                        TextStyle(fontFamily = Inter, fontWeight = FontWeight.Bold, fontSize = 36.sp, letterSpacing = (-0.5).sp),
                headlineLarge =
                        TextStyle(fontFamily = Inter, fontWeight = FontWeight.Bold, fontSize = 30.sp, letterSpacing = 0.sp),
                headlineMedium =
                        TextStyle(fontFamily = Inter, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, letterSpacing = 0.sp),
                headlineSmall =
                        TextStyle(fontFamily = Inter, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, letterSpacing = 0.sp),
                titleLarge =
                        TextStyle(
                                fontFamily = Inter,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                        ),
                titleMedium =
                        TextStyle(
                                fontFamily = Inter,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                        ),
                titleSmall =
                        TextStyle(
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                        ),
                bodyLarge = TextStyle(fontFamily = Inter, fontSize = 16.sp),
                bodyMedium = TextStyle(fontFamily = Inter, fontSize = 14.sp),
                bodySmall = TextStyle(fontFamily = Inter, fontSize = 12.sp),
                labelLarge =
                        TextStyle(
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp
                        ),
                labelMedium =
                        TextStyle(
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 11.sp
                        ),
                labelSmall =
                        TextStyle(
                                fontFamily = Inter,
                                fontWeight = FontWeight.Normal,
                                fontSize = 10.sp
                        ),
        )

@Composable
fun TextureOverlay(@DrawableRes texture: Int, alpha: Float, modifier: Modifier = Modifier) {
        Image(
                painter = painterResource(texture),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = modifier.fillMaxSize(),
                alpha = alpha
        )
}

@Composable
fun QuestCard(
        modifier: Modifier = Modifier,
        onClick: () -> Unit = {},
        content: @Composable BoxScope.() -> Unit
) {
        GlassCard(modifier = modifier.bounceClickable(onClick = onClick)) {
                TextureOverlay(
                        texture = R.drawable.tex_paper_noise,
                        alpha = if (isSystemInDarkTheme()) 0.03f else 0.02f
                )

                this.content()
        }
}
