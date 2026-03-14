package com.aspharier.questlife.domain.model

enum class HabitDifficulty(val xpReward: Int) {
    EASY(15),
    MEDIUM(30),
    HARD(50),
    VERY_HARD(75),
    EXTREME(100)
}