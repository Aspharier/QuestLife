package com.aspharier.questlife.domain.usecase

import kotlin.math.pow

object ProgressionSystem {

    fun xpRequiredForLevel(level: Int): Int {
        return (100 * level * 1.5.pow(level / 10.0)).toInt()
    }

    fun calculateLevel(totalXp: Int): Int {
        var level = 1
        var xpAccumulated = 0

        while (xpAccumulated + xpRequiredForLevel(level + 1) <= totalXp) {
            xpAccumulated += xpRequiredForLevel(level + 1)
            level++
        }
        return level
    }

    fun currentLevelProgress(totalXp: Int): Float {
        val level = calculateLevel(totalXp)

        val xpForCurrentLevel =
            (1..level).sumOf { xpRequiredForLevel(it) }

        val xpForNextLevel =
            (1..level + 1).sumOf { xpRequiredForLevel(it) }

        val xpInCurrentLevel =
            totalXp - xpForCurrentLevel

        val xpNeededForNextLevel =
            xpForNextLevel - xpForCurrentLevel

        return xpInCurrentLevel.toFloat() /
                xpNeededForNextLevel
    }
}