package com.aspharier.questlife.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspharier.questlife.domain.repository.EquipmentRepository
import com.aspharier.questlife.domain.repository.HabitRepository
import com.aspharier.questlife.domain.repository.UserRepository
import com.aspharier.questlife.domain.usecase.CalculateStatsUseCase
import com.aspharier.questlife.domain.usecase.CalculateTotalStatsUseCase
import com.aspharier.questlife.domain.usecase.ProgressionSystem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
        private val userRepository: UserRepository,
        private val habitRepository: HabitRepository,
        private val equipmentRepository: EquipmentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    private val calculateStatsUseCase = CalculateStatsUseCase()
    private val calculateTotalStatsUseCase = CalculateTotalStatsUseCase()
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private var previousLevel = 1

    init {
        observeXp()
    }

    private fun observeXp() {
        viewModelScope.launch {
            combine(
                            userRepository.observeTotalXp(),
                            habitRepository.observeHabits(),
                            equipmentRepository.observeEquipped(),
                            userRepository.observePersona()
                    ) { totalXp, habits, equipped, persona ->
                val level = ProgressionSystem.calculateLevel(totalXp)
                val progress = ProgressionSystem.currentLevelProgress(totalXp)
                val baseStats = calculateStatsUseCase(level, habits)
                val finalStats = calculateTotalStatsUseCase(baseStats, equipped)
                val levelUp = level > previousLevel
                previousLevel = level

                // Unlock any items the player is now eligible for
                equipmentRepository.unlockIfEligible(level)

                ProfileUiState(
                        level = level,
                        totalXp = totalXp,
                        progressToNextLevel = progress,
                        levelUp = levelUp,
                        stats = finalStats,
                        persona = persona
                )
            }
                    .collect { state -> _uiState.value = state }
        }
    }

    fun updatePersona(persona: com.aspharier.questlife.domain.model.Persona) {
        viewModelScope.launch {
            userRepository.updatePersona(persona)
        }
    }
}
