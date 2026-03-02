package com.aspharier.questlife.domain.repository

import com.aspharier.questlife.domain.model.Completion
import com.aspharier.questlife.domain.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {

    /** Observe all active habits */
    fun observeHabits(): Flow<List<Habit>>

    /** Insert or update a habit */
    suspend fun saveHabit(habit: Habit)

    /** Soft delete (deactivate) a habit */
    suspend fun deactivateHabit(habitId: String)

    /** Observe completion history for a habit */
    fun observeCompletion(habitId: String): Flow<List<Completion>>

    /** Add a new completion entry */
    suspend fun addCompletion(completion: Completion)

    /** Get the current streak for a habit (0 if never completed) */
    suspend fun getStreakForHabit(habitId: String): Int

    /** Returns true if the habit was already completed today */
    suspend fun isCompletedToday(habitId: String): Boolean

    /** Observe all completions (for quests / stats) */
    fun observeAllCompletions(): Flow<List<Completion>>
}
