package com.aspharier.questlife.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.aspharier.questlife.data.local.dao.CompletionDao
import com.aspharier.questlife.data.local.entities.CompletionEntity
import com.aspharier.questlife.domain.model.Persona
import com.aspharier.questlife.domain.repository.UserRepository
import com.aspharier.questlife.presentation.avatar.AvatarClass
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl @Inject constructor(
    private val completionDao: CompletionDao,
    private val dataStore: DataStore<Preferences>
) : UserRepository {

    private object PreferencesKeys {
        val USER_TITLE = stringPreferencesKey("user_title")
        val AVATAR_CLASS = stringPreferencesKey("avatar_class")
        val SELECTED_WARRIOR = stringPreferencesKey("selected_warrior")
    }

    override fun observeTotalXp(): Flow<Int> {
        return completionDao.observeAllCompletions().map { completions ->
            completions.sumOf { it.xpAwarded }
        }
    }

    override suspend fun addXp(amount: Int) {
        completionDao.insertCompletion(
                CompletionEntity(
                        id = UUID.randomUUID().toString(),
                        habitId = "system_quest_reward",
                        completedAt = System.currentTimeMillis(),
                        xpAwarded = amount,
                        currentStreak = 0,
                        wasFreezeUsed = false
                )
        )
    }

    override fun observePersona(): Flow<Persona> {
        return dataStore.data.map { preferences ->
            val title = preferences[PreferencesKeys.USER_TITLE] ?: "Warrior"
            val avatarClass = try {
                AvatarClass.valueOf(preferences[PreferencesKeys.AVATAR_CLASS] ?: AvatarClass.WARRIOR.name)
            } catch (e: Exception) {
                AvatarClass.WARRIOR
            }
            val selectedWarrior = preferences[PreferencesKeys.SELECTED_WARRIOR] ?: "Novice"
            Persona(title, avatarClass, selectedWarrior)
        }
    }

    override suspend fun updatePersona(persona: Persona) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_TITLE] = persona.title
            preferences[PreferencesKeys.AVATAR_CLASS] = persona.avatarClass.name
            preferences[PreferencesKeys.SELECTED_WARRIOR] = persona.selectedWarrior
        }
    }
}
