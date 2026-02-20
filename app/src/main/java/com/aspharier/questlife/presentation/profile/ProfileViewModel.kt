package com.aspharier.questlife.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspharier.questlife.domain.repository.UserRepository
import com.aspharier.questlife.domain.usecase.ProgressionSystem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> =
        _uiState.asStateFlow()

    private var previousLevel = 1

    init {
        observeXp()
    }

    private fun observeXp() {
        viewModelScope.launch {
            repository.observeTotalXp()
                .collect { totalXp ->

                    val level =
                        ProgressionSystem.calculateLevel(totalXp)

                    val progress =
                        ProgressionSystem.currentLevelProgress(totalXp)

                    val levelUp =
                        level > previousLevel

                    previousLevel = level

                    _uiState.value =
                        ProfileUiState(
                            level = level,
                            totalXp = totalXp,
                            progressToNextLevel = progress,
                            levelUp = levelUp
                        )
                }
        }
    }
}
