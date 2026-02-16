package com.aspharier.questlife.presentation.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspharier.questlife.domain.model.Habit
import com.aspharier.questlife.domain.model.HabitCategory
import com.aspharier.questlife.domain.model.HabitFrequency
import com.aspharier.questlife.domain.repository.HabitRepository
import com.aspharier.questlife.domain.usecase.CompleteHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HabitsViewModel @Inject constructor(
    private val repository: HabitRepository,
    private val completeHabitUseCase: CompleteHabitUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HabitsUiState())
    val uiState: StateFlow<HabitsUiState> = _uiState.asStateFlow()
    private val _completionState = MutableStateFlow(HabitCompletionState())
    val completionState: StateFlow<HabitCompletionState> =
        _completionState.asStateFlow()

    init {
        observeHabits()
    }

    private fun observeHabits() {
        repository.observeHabits()
            .onEach { habits ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        habits = habits,
                        error = null
                    )
                }
            }
            .catch { throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = throwable.message
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: HabitsEvent) {
        when (event) {
            is HabitsEvent.CreateHabit -> {
                viewModelScope.launch {
                    val newHabit = Habit(
                        id = UUID.randomUUID().toString(),
                        name = event.name,
                        iconId = "default",
                        colorHex = "#6366F1",

                        frequency = HabitFrequency.DAILY,
                        frequencyDays = emptyList(),
                        difficulty = event.difficulty,
                        category = HabitCategory.PRODUCTIVITY,

                        reminderTimes = emptyList(),
                        description = null,

                        createdAt = System.currentTimeMillis(),
                        isActive = true,
                        sortOrder = 0
                    )

                    repository.saveHabit(newHabit)
                }
            }
            is HabitsEvent.DeactivateHabit -> {
                viewModelScope.launch {
                    repository.deactivateHabit(event.habitId)
                }
            }
            is HabitsEvent.CompleteHabit -> {
                viewModelScope.launch {
                    val completion = completeHabitUseCase(event.habit)

                    completion?.let {
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
}