package com.aspharier.questlife.domain.usecase

import com.aspharier.questlife.domain.model.Habit
import com.aspharier.questlife.domain.model.UserStats

class CalculateStatsUseCase {
    operator fun invoke(
        level: Int,
        habits: List<Habit>
    ): UserStats {
        val baseHP = 100 + (level * 10)

        var atk = 10
        var def = 10
        var mana = 10
        var luck = 10

        habits.forEach { habit ->
            when (habit.category.name) {
                "PRODUCTIVITY" -> atk += 2
                "HEALTH" -> def += 2
                "CREATIVE" -> mana += 2
                "SOCIAL" -> luck += 2
                "FITNESS" -> def += 1
                "LEARNING" -> mana += 1
            }
        }

        return UserStats (
            hp = baseHP,
            atk = atk,
            def = def,
            mana = mana,
            luck = luck
        )
    }
}