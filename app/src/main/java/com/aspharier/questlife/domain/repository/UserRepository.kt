package com.aspharier.questlife.domain.repository

import com.aspharier.questlife.domain.model.Persona
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeTotalXp(): Flow<Int>
    suspend fun addXp(amount: Int)
    
    fun observePersona(): Flow<Persona>
    suspend fun updatePersona(persona: Persona)
}
