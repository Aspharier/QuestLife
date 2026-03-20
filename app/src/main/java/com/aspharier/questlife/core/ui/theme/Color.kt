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

// ── Green Dark Theme Palette (Forest RPG Fantasy) ──────────────────────────────

val GreenDarkColorScheme =
        darkColorScheme(
                primary = Color(0xFF4ADE80), // Green 400
                onPrimary = Color(0xFF064E3B),
                primaryContainer = Color(0xFF065F46),
                onPrimaryContainer = Color(0xFFD1FAE5),
                secondary = Color(0xFFFBBF24), // Amber 400
                onSecondary = Color(0xFF78350F),
                secondaryContainer = Color(0xFF92400E),
                onSecondaryContainer = Color(0xFFFEF3C7),
                tertiary = Color(0xFF6EE7B7), // Emerald 300
                onTertiary = Color(0xFF022C22),
                tertiaryContainer = Color(0xFF064E3B),
                onTertiaryContainer = Color(0xFFA7F3D0),
                background = Color(0xFF061510), // Deep forest-black
                onBackground = Color(0xFFE2E8F0),
                surface = Color(0xFF0B1F17), // Deep forest panel
                onSurface = Color(0xFFE2E8F0),
                surfaceVariant = Color(0xFF163228),
                onSurfaceVariant = Color(0xFFA7F3D0),
                outline = Color(0xFF28483B),
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
        val screenBackgroundGradient: List<Color>,
        val particleColors: List<Color>,
        val glowPrimary: Color,
        val glowSecondary: Color,
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
                screenBackgroundGradient = listOf(
                        Color(0xFF130B1E),
                        Color(0xFF1C122A),
                        Color(0xFF2E1E45).copy(alpha = 0.5f),
                        Color(0xFF581C87).copy(alpha = 0.2f)
                ),
                particleColors = listOf(
                        Color(0xFFC084FC),
                        Color(0xFFF472B6),
                        Color(0xFF818CF8).copy(alpha = 0.5f)
                ),
                glowPrimary = Color(0xFFC084FC),
                glowSecondary = Color(0xFFF472B6),
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
                screenBackgroundGradient = listOf(
                        Color(0xFF0A0E1A),
                        Color(0xFF121829),
                        Color(0xFF1C2333).copy(alpha = 0.5f),
                        Color(0xFF312E81).copy(alpha = 0.2f)
                ),
                particleColors = listOf(
                        Color(0xFF818CF8),
                        Color(0xFFF472B6),
                        Color(0xFFA78BFA).copy(alpha = 0.5f)
                ),
                glowPrimary = Color(0xFF818CF8),
                glowSecondary = Color(0xFFF472B6),
        )

val GreenDarkGameColors =
        GameColors(
                panelBackground = Color(0xFF0E2219),
                panelBorder = Color(0xFF244234),
                panelBorderGlow = Color(0x444ADE80),
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
                accentGlow = Color(0x334ADE80),
                fabGlow = Color(0xFF4ADE80),
                navBarBackground = Color(0xFF081812),
                navBarBorder = Color(0xFF163228),
                navBarSelected = Color(0xFF4ADE80),
                navBarUnselected = Color(0xFF5A8A7A),
                sectionDivider = Color(0x334ADE80),
                chipSelected = Color(0x334ADE80),
                chipBorder = Color(0xFF244234),
                completedGreen = Color(0xFF34D399),
                screenBackgroundGradient = listOf(
                        Color(0xFF061510),
                        Color(0xFF0B1F17),
                        Color(0xFF163228).copy(alpha = 0.5f),
                        Color(0xFF065F46).copy(alpha = 0.2f)
                ),
                particleColors = listOf(
                        Color(0xFF4ADE80),
                        Color(0xFFFBBF24),
                        Color(0xFF6EE7B7).copy(alpha = 0.5f)
                ),
                glowPrimary = Color(0xFF4ADE80),
                glowSecondary = Color(0xFFFBBF24),
        )

// ── Crimson Night Theme Palette ──────────────────────────────
val CrimsonNightColorScheme = darkColorScheme(
        primary = Color(0xFFEF4444),
        onPrimary = Color(0xFF450A0A),
        primaryContainer = Color(0xFF7F1D1D),
        onPrimaryContainer = Color(0xFFFEE2E2),
        secondary = Color(0xFFF97316),
        onSecondary = Color(0xFF431407),
        secondaryContainer = Color(0xFF7C2D12),
        onSecondaryContainer = Color(0xFFFFEDD5),
        tertiary = Color(0xFFF43F5E),
        onTertiary = Color(0xFF4C0519),
        tertiaryContainer = Color(0xFF881337),
        onTertiaryContainer = Color(0xFFFFE4E6),
        background = Color(0xFF140A0A),
        onBackground = Color(0xFFF3F4F6),
        surface = Color(0xFF1F1111),
        onSurface = Color(0xFFF3F4F6),
        surfaceVariant = Color(0xFF3B1E1E),
        onSurfaceVariant = Color(0xFFD1D5DB),
        outline = Color(0xFF5C2D2D),
        error = Color(0xFFF87171),
        onError = Color(0xFF450A0A),
        errorContainer = Color(0xFF7F1D1D),
        onErrorContainer = Color(0xFFFEE2E2),
)

// ── Ocean Depths Theme Palette ──────────────────────────────
val OceanDepthsColorScheme = darkColorScheme(
        primary = Color(0xFF06B6D4),
        onPrimary = Color(0xFF083344),
        primaryContainer = Color(0xFF164E63),
        onPrimaryContainer = Color(0xFFCFFAFE),
        secondary = Color(0xFF3B82F6),
        onSecondary = Color(0xFF172554),
        secondaryContainer = Color(0xFF1E3A8A),
        onSecondaryContainer = Color(0xFFDBEAFE),
        tertiary = Color(0xFF0EA5E9),
        onTertiary = Color(0xFF0C4A6E),
        tertiaryContainer = Color(0xFF075985),
        onTertiaryContainer = Color(0xFFE0F2FE),
        background = Color(0xFF08121A),
        onBackground = Color(0xFFF1F5F9),
        surface = Color(0xFF0F1E2E),
        onSurface = Color(0xFFF1F5F9),
        surfaceVariant = Color(0xFF1B314A),
        onSurfaceVariant = Color(0xFFCBD5E1),
        outline = Color(0xFF2C4A6A),
        error = Color(0xFFF87171),
        onError = Color(0xFF450A0A),
        errorContainer = Color(0xFF7F1D1D),
        onErrorContainer = Color(0xFFFEE2E2),
)

// ── Sunset Blaze Theme Palette ──────────────────────────────
val SunsetBlazeColorScheme = darkColorScheme(
        primary = Color(0xFFF97316),
        onPrimary = Color(0xFF431407),
        primaryContainer = Color(0xFF7C2D12),
        onPrimaryContainer = Color(0xFFFFEDD5),
        secondary = Color(0xFFD946EF),
        onSecondary = Color(0xFF4A044E),
        secondaryContainer = Color(0xFF86198F),
        onSecondaryContainer = Color(0xFFFAEAFF),
        tertiary = Color(0xFFF59E0B),
        onTertiary = Color(0xFF451A03),
        tertiaryContainer = Color(0xFF78350F),
        onTertiaryContainer = Color(0xFFFEF3C7),
        background = Color(0xFF1A0F0A),
        onBackground = Color(0xFFF3F4F6),
        surface = Color(0xFF26150F),
        onSurface = Color(0xFFF3F4F6),
        surfaceVariant = Color(0xFF402217),
        onSurfaceVariant = Color(0xFFD1D5DB),
        outline = Color(0xFF633221),
        error = Color(0xFFF87171),
        onError = Color(0xFF450A0A),
        errorContainer = Color(0xFF7F1D1D),
        onErrorContainer = Color(0xFFFEE2E2),
)

// ── Neon Cyber Theme Palette ──────────────────────────────
val NeonCyberColorScheme = darkColorScheme(
        primary = Color(0xFF2DD4BF),
        onPrimary = Color(0xFF042F2E),
        primaryContainer = Color(0xFF115E59),
        onPrimaryContainer = Color(0xFFCCFBF1),
        secondary = Color(0xFFF472B6),
        onSecondary = Color(0xFF500724),
        secondaryContainer = Color(0xFF831843),
        onSecondaryContainer = Color(0xFFFCE7F3),
        tertiary = Color(0xFF818CF8),
        onTertiary = Color(0xFF1E1B4B),
        tertiaryContainer = Color(0xFF312E81),
        onTertiaryContainer = Color(0xFFE0E7FF),
        background = Color(0xFF060514),
        onBackground = Color(0xFFF3F4F6),
        surface = Color(0xFF0D0B24),
        onSurface = Color(0xFFF3F4F6),
        surfaceVariant = Color(0xFF1B1645),
        onSurfaceVariant = Color(0xFF9CA3AF),
        outline = Color(0xFF332970),
        error = Color(0xFFF87171),
        onError = Color(0xFF450A0A),
        errorContainer = Color(0xFF7F1D1D),
        onErrorContainer = Color(0xFFFEE2E2),
)

val CrimsonNightGameColors = GameColors(
        panelBackground = Color(0xFF140A0A),
        panelBorder = Color(0xFF311111),
        panelBorderGlow = Color(0x44EF4444),
        healthBar = Color(0xFFFF4757), manaBar = Color(0xFF5B9BF7), xpBar = Color(0xFFFFD700), xpBarSecondary = Color(0xFFFFA000), staminaBar = Color(0xFF34D399), streakFlame = Color(0xFFFF8C57), rarityCommon = Color(0xFF9E9E9E), rarityRare = Color(0xFF60A5FA), rarityEpic = Color(0xFFA78BFA), rarityLegendary = Color(0xFFFB923C),
        accentGlow = Color(0x33EF4444), fabGlow = Color(0xFFEF4444),
        navBarBackground = Color(0xFF0D0505), navBarBorder = Color(0xFF2B0C0C), navBarSelected = Color(0xFFEF4444), navBarUnselected = Color(0xFF7B3838),
        sectionDivider = Color(0x33EF4444), chipSelected = Color(0x33EF4444), chipBorder = Color(0xFF311111), completedGreen = Color(0xFF34D399),
        screenBackgroundGradient = listOf(Color(0xFF140A0A), Color(0xFF1F1111), Color(0xFF3B1E1E).copy(alpha = 0.5f), Color(0xFF7F1D1D).copy(alpha = 0.2f)),
        particleColors = listOf(Color(0xFFEF4444), Color(0xFFF97316), Color(0xFFF43F5E).copy(alpha = 0.5f)),
        glowPrimary = Color(0xFFEF4444), glowSecondary = Color(0xFFF97316),
)

val OceanDepthsGameColors = GameColors(
        panelBackground = Color(0xFF08121A),
        panelBorder = Color(0xFF132A3D),
        panelBorderGlow = Color(0x4406B6D4),
        healthBar = Color(0xFFFF4757), manaBar = Color(0xFF5B9BF7), xpBar = Color(0xFFFFD700), xpBarSecondary = Color(0xFFFFA000), staminaBar = Color(0xFF34D399), streakFlame = Color(0xFFFF8C57), rarityCommon = Color(0xFF9E9E9E), rarityRare = Color(0xFF60A5FA), rarityEpic = Color(0xFFA78BFA), rarityLegendary = Color(0xFFFB923C),
        accentGlow = Color(0x3306B6D4), fabGlow = Color(0xFF06B6D4),
        navBarBackground = Color(0xFF050A0E), navBarBorder = Color(0xFF112435), navBarSelected = Color(0xFF06B6D4), navBarUnselected = Color(0xFF31526E),
        sectionDivider = Color(0x3306B6D4), chipSelected = Color(0x3306B6D4), chipBorder = Color(0xFF132A3D), completedGreen = Color(0xFF34D399),
        screenBackgroundGradient = listOf(Color(0xFF08121A), Color(0xFF0F1E2E), Color(0xFF1B314A).copy(alpha = 0.5f), Color(0xFF164E63).copy(alpha = 0.2f)),
        particleColors = listOf(Color(0xFF06B6D4), Color(0xFF3B82F6), Color(0xFF0EA5E9).copy(alpha = 0.5f)),
        glowPrimary = Color(0xFF06B6D4), glowSecondary = Color(0xFF3B82F6),
)

val SunsetBlazeGameColors = GameColors(
        panelBackground = Color(0xFF1A0F0A),
        panelBorder = Color(0xFF381C0F),
        panelBorderGlow = Color(0x44F97316),
        healthBar = Color(0xFFFF4757), manaBar = Color(0xFF5B9BF7), xpBar = Color(0xFFFFD700), xpBarSecondary = Color(0xFFFFA000), staminaBar = Color(0xFF34D399), streakFlame = Color(0xFFFF8C57), rarityCommon = Color(0xFF9E9E9E), rarityRare = Color(0xFF60A5FA), rarityEpic = Color(0xFFA78BFA), rarityLegendary = Color(0xFFFB923C),
        accentGlow = Color(0x33F97316), fabGlow = Color(0xFFF97316),
        navBarBackground = Color(0xFF0D0705), navBarBorder = Color(0xFF261209), navBarSelected = Color(0xFFF97316), navBarUnselected = Color(0xFF823E21),
        sectionDivider = Color(0x33F97316), chipSelected = Color(0x33F97316), chipBorder = Color(0xFF381C0F), completedGreen = Color(0xFF34D399),
        screenBackgroundGradient = listOf(Color(0xFF1A0F0A), Color(0xFF26150F), Color(0xFF402217).copy(alpha = 0.5f), Color(0xFF7C2D12).copy(alpha = 0.2f)),
        particleColors = listOf(Color(0xFFF97316), Color(0xFFD946EF), Color(0xFFF59E0B).copy(alpha = 0.5f)),
        glowPrimary = Color(0xFFF97316), glowSecondary = Color(0xFFD946EF),
)

val NeonCyberGameColors = GameColors(
        panelBackground = Color(0xFF060514),
        panelBorder = Color(0xFF15103E),
        panelBorderGlow = Color(0x442DD4BF),
        healthBar = Color(0xFFFF4757), manaBar = Color(0xFF5B9BF7), xpBar = Color(0xFFFFD700), xpBarSecondary = Color(0xFFFFA000), staminaBar = Color(0xFF34D399), streakFlame = Color(0xFFFF8C57), rarityCommon = Color(0xFF9E9E9E), rarityRare = Color(0xFF60A5FA), rarityEpic = Color(0xFFA78BFA), rarityLegendary = Color(0xFFFB923C),
        accentGlow = Color(0x332DD4BF), fabGlow = Color(0xFF2DD4BF),
        navBarBackground = Color(0xFF04030D), navBarBorder = Color(0xFF110C30), navBarSelected = Color(0xFF2DD4BF), navBarUnselected = Color(0xFF362B75),
        sectionDivider = Color(0x332DD4BF), chipSelected = Color(0x332DD4BF), chipBorder = Color(0xFF15103E), completedGreen = Color(0xFF34D399),
        screenBackgroundGradient = listOf(Color(0xFF060514), Color(0xFF0D0B24), Color(0xFF1B1645).copy(alpha = 0.5f), Color(0xFF115E59).copy(alpha = 0.2f)),
        particleColors = listOf(Color(0xFF2DD4BF), Color(0xFFF472B6), Color(0xFF818CF8).copy(alpha = 0.5f)),
        glowPrimary = Color(0xFF2DD4BF), glowSecondary = Color(0xFFF472B6),
)

val LocalGameColors = staticCompositionLocalOf { DarkGameColors }
