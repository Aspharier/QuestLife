package com.aspharier.questlife.app.di

import com.aspharier.questlife.data.repository.EquipmentRepositoryImpl
import com.aspharier.questlife.data.repository.HabitRepositoryImpl
import com.aspharier.questlife.data.repository.QuestRepositoryImpl
import com.aspharier.questlife.data.repository.UserRepositoryImpl
import com.aspharier.questlife.domain.repository.EquipmentRepository
import com.aspharier.questlife.domain.repository.HabitRepository
import com.aspharier.questlife.domain.repository.QuestRepository
import com.aspharier.questlife.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton abstract fun bindHabitRepository(impl: HabitRepositoryImpl): HabitRepository
    @Binds @Singleton abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
    @Binds
    @Singleton
    abstract fun bindEquipmentRepository(impl: EquipmentRepositoryImpl): EquipmentRepository
    @Binds @Singleton abstract fun bindQuestRepository(impl: QuestRepositoryImpl): QuestRepository
}
