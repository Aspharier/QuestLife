package com.aspharier.questlife.presentation.profile

data class ProfileUiState(
    val level: Int = 1,
    val totalXp: Int = 0,
    val progressToNextLevel: Float = 0f,
    val levelUp: Boolean = false
)


