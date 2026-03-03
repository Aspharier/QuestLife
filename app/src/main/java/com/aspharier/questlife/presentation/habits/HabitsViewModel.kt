package com.aspharier.questlife.presentation.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspharier.questlife.domain.model.Habit
import com.aspharier.questlife.domain.model.HabitCategory
import com.aspharier.questlife.domain.model.HabitFrequency
import com.aspharier.questlife.domain.model.HabitWithStreak
import com.aspharier.questlife.domain.model.Quest
import com.aspharier.questlife.domain.repository.HabitRepository
import com.aspharier.questlife.domain.usecase.CompleteHabitUseCase
import com.aspharier.questlife.domain.usecase.QuestProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HabitsViewModel
@Inject
constructor(
        private val repository: HabitRepository,
        private val completeHabitUseCase: CompleteHabitUseCase,
        private val questProgressUseCase: QuestProgressUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HabitsUiState())
    val uiState: StateFlow<HabitsUiState> = _uiState.asStateFlow()

    private val _completionState = MutableStateFlow(HabitCompletionState())
    val completionState: StateFlow<HabitCompletionState> = _completionState.asStateFlow()

    private val _newlyCompletedQuests = MutableStateFlow<List<Quest>>(emptyList())
    val newlyCompletedQuests: StateFlow<List<Quest>> = _newlyCompletedQuests.asStateFlow()

    init {
        observeHabits()
    }

    private fun observeHabits() {
        repository
                .observeHabits()
                .onEach { habits ->
                    val today = LocalDate.now()
                    val todayDayName = today.dayOfWeek.name // e.g., "MONDAY"

                    val todayHabits =
                            habits.filter { habit ->
                                when (habit.frequency) {
                                    HabitFrequency.DAILY -> true
                                    HabitFrequency.WEEKLY ->
                                            habit.frequencyDays.contains(todayDayName)
                                    HabitFrequency.CUSTOM ->
                                            habit.frequencyDays.contains(todayDayName)
                                }
                            }

                    val habitsWithStreak =
                            todayHabits.map { habit ->
                                val streak = repository.getStreakForHabit(habit.id)
                                val completedToday = repository.isCompletedToday(habit.id)
                                HabitWithStreak(
                                        habit = habit,
                                        currentStreak = streak,
                                        isCompletedToday = completedToday
                                )
                            }

                    _uiState.update {
                        it.copy(isLoading = false, habits = habitsWithStreak, error = null)
                    }
                }
                .catch { throwable ->
                    _uiState.update { it.copy(isLoading = false, error = throwable.message) }
                }
                .launchIn(viewModelScope)
    }

    fun onEvent(event: HabitsEvent) {
        when (event) {
            is HabitsEvent.CreateHabit -> {
                viewModelScope.launch {
                    val newHabit =
                            Habit(
                                    id = UUID.randomUUID().toString(),
                                    name = event.name,
                                    iconId = "default",
                                    colorHex = categoryColor(event.category),
                                    frequency = event.frequency,
                                    frequencyDays = event.frequencyDays,
                                    difficulty = event.difficulty,
                                    category = event.category,
                                    reminderTimes = emptyList(),
                                    description = event.description,
                                    createdAt = System.currentTimeMillis(),
                                    isActive = true,
                                    sortOrder = 0
                            )
                    repository.saveHabit(newHabit)
                }
            }
            is HabitsEvent.DeactivateHabit -> {
                viewModelScope.launch { repository.deactivateHabit(event.habitId) }
            }
            is HabitsEvent.CompleteHabit -> {
                viewModelScope.launch {
                    val completion = completeHabitUseCase(event.habit)
                    completion?.let {
                        // After completing a habit, evaluate quest progress!
                        val questsJustCompleted = questProgressUseCase()
                        if (questsJustCompleted.isNotEmpty()) {
                            _newlyCompletedQuests.value =
                                    _newlyCompletedQuests.value + questsJustCompleted
                        }

                        _completionState.value =
                                HabitCompletionState(
                                        completedHabitId = event.habit.id,
                                        xpGained = it.xpAwarded
                                )
                    }
                }
            }
        }
    }

    fun clearCompletionState() {
        _completionState.value = HabitCompletionState()
    }

    fun removeCompletedQuest(questId: String) {
        _newlyCompletedQuests.update { quests -> quests.filterNot { it.id == questId } }
    }

    private fun categoryColor(category: HabitCategory): String =
            when (category) {
                HabitCategory.HEALTH -> "#22C55E"
                HabitCategory.FITNESS -> "#F97316"
                HabitCategory.PRODUCTIVITY -> "#6366F1"
                HabitCategory.LEARNING -> "#3B82F6"
                HabitCategory.SOCIAL -> "#EC4899"
                HabitCategory.CREATIVE -> "#8B5CF6"
            }
}
