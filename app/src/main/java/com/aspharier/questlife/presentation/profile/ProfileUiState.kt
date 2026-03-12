package com.aspharier.questlife.presentation.profile

import com.aspharier.questlife.domain.model.Persona
import com.aspharier.questlife.domain.model.UserStats

data class ProfileUiState(
    val level: Int = 1,
    val totalXp: Int = 0,
    val progressToNextLevel: Float = 0f,
    val levelUp: Boolean = false,
    val stats: UserStats = UserStats(100 ,10, 10, 10, 10),
    val persona: Persona = Persona()
)


