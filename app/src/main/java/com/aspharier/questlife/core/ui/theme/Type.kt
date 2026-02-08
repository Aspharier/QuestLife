package com.aspharier.questlife.core.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.questlife.app.R

// Custom Typography

val Inter = FontFamily(
    Font(R.font.inter_regular),
    Font(R.font.inter_medium),
    Font(R.font.inter_bold)
)

val Pixel = FontFamily(
    Font(R.font.pixelify_sans)
)

val QuestLifeTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = Pixel,
        fontSize = 32.sp,
        letterSpacing = 1.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)

@Composable
fun TextureOverlay(
    @DrawableRes texture: Int,
    alpha: Float,
    modifier: Modifier = Modifier
) {
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
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        TextureOverlay(
            texture = R.drawable.tex_paper_noise,
            alpha = if (isSystemInDarkTheme()) 0.05f else 0.03f
        )

        content()
    }
}