package com.aspharier.questlife.presentation.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspharier.questlife.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitsViewModel @Inject constructor(
    private val repository: HabitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HabitsUiState())
    val uiState: StateFlow<HabitsUiState> = _uiState.asStateFlow()

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
                    repository.saveHabit(event.habit)
                }
            }
            is HabitsEvent.DeactivateHabit -> {
                viewModelScope.launch {
                    repository.deactivateHabit(event.habitId)
                }
            }
        }
    }
}