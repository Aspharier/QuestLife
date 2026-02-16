package com.aspharier.questlife.domain.usecase

import com.aspharier.questlife.domain.model.Completion
import com.aspharier.questlife.domain.model.Habit
import com.aspharier.questlife.domain.repository.HabitRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject

class CompleteHabitUseCase @Inject constructor(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(habit: Habit): Completion? {
        val todayStart = LocalDate.now()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val now = System.currentTimeMillis()

        // Check if already completed today
        val completions = repository
            .observeCompletion(habit.id)
            .first()

        val alreadyCompletedToday = completions.any {
            it.completedAt >= todayStart
        }

        if(alreadyCompletedToday) return null

        val completion = Completion(
            id = UUID.randomUUID().toString(),
            habitId = habit.id,
            completedAt = now,
            xpAwarded = habit.difficulty.xpReward,
            currentStreak = calculateStreak(completions, todayStart),
            wasFreezeUsed = false
        )
        repository.addCompletion(completion)

        return completion
    }

    private fun calculateStreak(
        completions: List<Completion>,
        todayStart: Long
    ): Int {
       val yesterdayStart = todayStart - 24 * 60 * 60 * 1000

        val hasYesterday = completions.any {
            it.completedAt in yesterdayStart until todayStart
        }

        return if (hasYesterday) {
            completions.maxOfOrNull { it.currentStreak }?.plus(1) ?: 1
        } else {
            1
        }
    }
}