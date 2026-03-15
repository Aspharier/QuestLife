package com.aspharier.questlife.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ── Game-specific semantic colors ──────────────────────────────────────────────

val healthRed = Color(0xFFFF4757)
val healthRedDark = Color(0xFFCC3945)
val manaBlue = Color(0xFF3B82F6)
val manaBlueDark = Color(0xFF5B9BF7)
val xpGold = Color(0xFFFFD700)
val xpGoldDark = Color(0xFFFFC107)
val shieldSilver = Color(0xFFB0BEC5)
val streakFlame = Color(0xFFFF6B35)
val staminaGreen = Color(0xFF22C55E)

val rareBlue = Color(0xFF3B82F6)
val epicPurple = Color(0xFF8B5CF6)
val legendaryOrange = Color(0xFFF97316)
val commonGray = Color(0xFF9E9E9E)

// ── Purple Dark Theme Palette (Mystic RPG Fantasy) ──────────────────────────────

val PurpleDarkColorScheme =
        darkColorScheme(
                primary = Color(0xFFC084FC), // Purple 400
                onPrimary = Color(0xFF3B0764),
                primaryContainer = Color(0xFF581C87),
                onPrimaryContainer = Color(0xFFF3E8FF),
                secondary = Color(0xFFF472B6), // Pink 400
                onSecondary = Color(0xFF500724),
                secondaryContainer = Color(0xFF831843),
                onSecondaryContainer = Color(0xFFFCE7F3),
                tertiary = Color(0xFF818CF8), // Indigo 400
                onTertiary = Color(0xFF1E1B4B),
                tertiaryContainer = Color(0xFF312E81),
                onTertiaryContainer = Color(0xFFE0E7FF),
                background = Color(0xFF130B1E), // Deep purple-black
                onBackground = Color(0xFFE8E5F0),
                surface = Color(0xFF1C122A), // Deep purple panel
                onSurface = Color(0xFFE8E5F0),
                surfaceVariant = Color(0xFF2E1E45),
                onSurfaceVariant = Color(0xFFBCA6D9),
                outline = Color(0xFF4C3A6B),
                error = Color(0xFFF87171),
                onError = Color(0xFF450A0A),
                errorContainer = Color(0xFF7F1D1D),
                onErrorContainer = Color(0xFFFEE2E2),
        )

// ── Dark Theme Palette (Deep RPG Fantasy) ──────────────────────────────────────

val DarkColorScheme =
        darkColorScheme(
                primary = Color(0xFF818CF8), // Indigo 400
                onPrimary = Color(0xFF1E1B4B),
                primaryContainer = Color(0xFF312E81),
                onPrimaryContainer = Color(0xFFE0E7FF),
                secondary = Color(0xFFF472B6), // Pink 400
                onSecondary = Color(0xFF500724),
                secondaryContainer = Color(0xFF831843),
                onSecondaryContainer = Color(0xFFFCE7F3),
                tertiary = Color(0xFFA78BFA), // Violet 400
                onTertiary = Color(0xFF2E1065),
                tertiaryContainer = Color(0xFF5B21B6),
                onTertiaryContainer = Color(0xFFEDE9FE),
                background = Color(0xFF0A0E1A), // Deep navy-black
                onBackground = Color(0xFFE8E5F0),
                surface = Color(0xFF121829), // Slate panel
                onSurface = Color(0xFFE8E5F0),
                surfaceVariant = Color(0xFF1C2333),
                onSurfaceVariant = Color(0xFF9CA3AF),
                outline = Color(0xFF2D3548),
                error = Color(0xFFF87171),
                onError = Color(0xFF450A0A),
                errorContainer = Color(0xFF7F1D1D),
                onErrorContainer = Color(0xFFFEE2E2),
        )

// ── Extended Game Colors (via CompositionLocal) ────────────────────────────────

@Immutable
data class GameColors(
        val panelBackground: Color,
        val panelBorder: Color,
        val panelBorderGlow: Color,
        val healthBar: Color,
        val manaBar: Color,
        val xpBar: Color,
        val xpBarSecondary: Color,
        val staminaBar: Color,
        val streakFlame: Color,
        val rarityCommon: Color,
        val rarityRare: Color,
        val rarityEpic: Color,
        val rarityLegendary: Color,
        val accentGlow: Color,
        val fabGlow: Color,
        val navBarBackground: Color,
        val navBarBorder: Color,
        val navBarSelected: Color,
        val navBarUnselected: Color,
        val sectionDivider: Color,
        val chipSelected: Color,
        val chipBorder: Color,
        val completedGreen: Color,
)

val PurpleDarkGameColors =
        GameColors(
                panelBackground = Color(0xFF1F1430),
                panelBorder = Color(0xFF3C275A),
                panelBorderGlow = Color(0x44C084FC),
                healthBar = Color(0xFFFF4757),
                manaBar = Color(0xFF5B9BF7),
                xpBar = Color(0xFFFFD700),
                xpBarSecondary = Color(0xFFFFA000),
                staminaBar = Color(0xFF34D399),
                streakFlame = Color(0xFFFF8C57),
                rarityCommon = Color(0xFF9E9E9E),
                rarityRare = Color(0xFF60A5FA),
                rarityEpic = Color(0xFFA78BFA),
                rarityLegendary = Color(0xFFFB923C),
                accentGlow = Color(0x33C084FC),
                fabGlow = Color(0xFFC084FC),
                navBarBackground = Color(0xFF150C22),
                navBarBorder = Color(0xFF2E1C45),
                navBarSelected = Color(0xFFC084FC),
                navBarUnselected = Color(0xFF7E60A8),
                sectionDivider = Color(0x33C084FC),
                chipSelected = Color(0x33C084FC),
                chipBorder = Color(0xFF3C275A),
                completedGreen = Color(0xFF34D399),
        )

val DarkGameColors =
        GameColors(
                panelBackground = Color(0xFF141B2D),
                panelBorder = Color(0xFF2A3350),
                panelBorderGlow = Color(0x44818CF8),
                healthBar = Color(0xFFFF4757),
                manaBar = Color(0xFF5B9BF7),
                xpBar = Color(0xFFFFD700),
                xpBarSecondary = Color(0xFFFFA000),
                staminaBar = Color(0xFF34D399),
                streakFlame = Color(0xFFFF8C57),
                rarityCommon = Color(0xFF9E9E9E),
                rarityRare = Color(0xFF60A5FA),
                rarityEpic = Color(0xFFA78BFA),
                rarityLegendary = Color(0xFFFB923C),
                accentGlow = Color(0x33818CF8),
                fabGlow = Color(0xFF818CF8),
                navBarBackground = Color(0xFF0D1220),
                navBarBorder = Color(0xFF1E2A45),
                navBarSelected = Color(0xFF818CF8),
                navBarUnselected = Color(0xFF4B5563),
                sectionDivider = Color(0x33818CF8),
                chipSelected = Color(0x33818CF8),
                chipBorder = Color(0xFF2D3548),
                completedGreen = Color(0xFF34D399),
        )

val LocalGameColors = staticCompositionLocalOf { DarkGameColors }
