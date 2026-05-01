package com.aspharier.questlife.data.repository

import com.aspharier.questlife.data.local.dao.CompletionDao
import com.aspharier.questlife.data.local.dao.HabitDao
import com.aspharier.questlife.data.local.entities.CompletionEntity
import com.aspharier.questlife.data.mapper.CompletionMapper.toDomain
import com.aspharier.questlife.data.mapper.HabitMapper.toDomain
import com.aspharier.questlife.data.mapper.HabitMapper.toEntity
import com.aspharier.questlife.domain.model.Completion
import com.aspharier.questlife.domain.model.Habit
import com.aspharier.questlife.domain.repository.HabitRepository
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HabitRepositoryImpl
@Inject
constructor(private val habitDao: HabitDao, private val completionDao: CompletionDao) :
        HabitRepository {

    override fun observeHabits(): Flow<List<Habit>> {
        return habitDao.observeHabits().map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun saveHabit(habit: Habit) {
        habitDao.insertHabit(habit.toEntity())
    }

    override suspend fun deactivateHabit(habitId: String) {
        habitDao.deactivateHabit(habitId)
    }

    override fun observeCompletion(habitId: String): Flow<List<Completion>> {
        return completionDao.observeCompletionsFroHabit(habitId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addCompletion(completion: Completion) {
        completionDao.insertCompletion(
                CompletionEntity(
                        id = completion.id,
                        habitId = completion.habitId,
                        completedAt = completion.completedAt,
                        xpAwarded = completion.xpAwarded,
                        currentStreak = completion.currentStreak,
                        wasFreezeUsed = completion.wasFreezeUsed
                )
        )
    }

    override suspend fun getStreakForHabit(habitId: String): Int {
        return completionDao.getLatestStreakForHabit(habitId) ?: 0
    }

    override suspend fun isCompletedToday(habitId: String): Boolean {
        val todayStart =
                LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return completionDao.countCompletionsTodayForHabit(habitId, todayStart) > 0
    }

    override fun observeAllCompletions(): Flow<List<Completion>> {
        return completionDao.observeAllCompletions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getCompletionCountSince(startMs: Long): Int =
            completionDao.getCompletionCountSince(startMs)

    override suspend fun getMaxStreakAcrossAllHabits(): Int = completionDao.getMaxStreak()

    override suspend fun getTotalCompletionCount(): Int = completionDao.getTotalCompletionCount()

    override suspend fun getTotalHabitsCreated(): Int = habitDao.getCount()

    override suspend fun getActiveHabitsCount(): Int = habitDao.getActiveCount()
}
