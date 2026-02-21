package com.aspharier.questlife.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspharier.questlife.domain.repository.HabitRepository
import com.aspharier.questlife.domain.repository.UserRepository
import com.aspharier.questlife.domain.usecase.CalculateStatsUseCase
import com.aspharier.questlife.domain.usecase.ProgressionSystem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    private val calculateStatsUseCase = CalculateStatsUseCase()
    val uiState: StateFlow<ProfileUiState> =
        _uiState.asStateFlow()

    private var previousLevel = 1

    init {
        observeXp()
    }

    private fun observeXp() {
        viewModelScope.launch {
            combine(
                userRepository.observeTotalXp(),
                habitRepository.observeHabits()
            ) { totalXp, habits ->
                val level = ProgressionSystem.calculateLevel(totalXp)
                val progress = ProgressionSystem.currentLevelProgress(totalXp)
                val stats = calculateStatsUseCase(level, habits)
                val levelUp = level > previousLevel
                previousLevel = level

                ProfileUiState(
                    level = level,
                    totalXp = totalXp,
                    progressToNextLevel = progress,
                    levelUp = levelUp,
                    stats = stats
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}